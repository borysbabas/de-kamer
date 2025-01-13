package com.example.backend.controller;

import com.example.backend.DTO.AccountDTO;
import com.example.backend.config.ApiConfig;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Account;
import com.example.backend.services.AccountService;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * RestController that handles endpoints related to the account details page.
 * Provides functionality for retrieving, updating and deleting accounts.
 */
@RestController
public class AccountDetailsController {
    private final AccountService accountService;
    private final PostService postService;

    /**
     * Constructor that initializes the controller object.
     * @param accountService service for handling accounts.
     */
    @Autowired
    public AccountDetailsController(AccountService accountService, PostService postService) {
        this.accountService = accountService;
        this.postService = postService;
    }

    /**
     * Retrieves a single account by its ID.
     * @param id id of the account to retrieve.
     * @return the account if found and authorized , or null.
     * catches a jwtException when an error is encountered in the jwt.
     */
    @GetMapping(path = ApiPaths.ACCOUNT_DETAILS)
    public AccountDTO getAccountById(@PathVariable Long id) {
        return new AccountDTO(Objects.requireNonNull(accountService.findAccountById(id)
                .stream()
                .findFirst()
                .orElse(null)));
    }

    /**
     * Method to delete a single account by the corresponding id
     * @param authorizationHeader the header that contains the jwt
     * @param id the provided id of the account that needs to be deleted.
     * @return a boolean value. if the deletion is successful it returns true, false if not.
     */
    @DeleteMapping(path = ApiPaths.DELETE_ACCOUNT)
    public boolean deleteAccount(@RequestAttribute(ApiConfig.ACCOUNT_HEADER) Account account) {
        postService.deletePostsByAccountId(account.getId());
        return accountService.deleteAccountById(account.getId());
    }

    /**
     * Method to update values of  certain account.
     * @param authorizationHeader Authorization header containing the jwt.
     * @param id the id of the account to act on.
     * @param payload Map containing the field to update and its new value .
     *                Example: {"field": "email", "value": "newEmailExample@gmail.com"}
     * @return A ResponseEntity indicating the status of the method's execution.
     */
    @PutMapping(path= ApiPaths.ACCOUNTDETAILS_SERVICE_UPDATE)
    public ResponseEntity<?> updateUserDetails(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader,@PathVariable(value = "id", required = true)Long id, @RequestBody Map<String, String> payload){
        String field = payload.get("field");
        String value = payload.get("value");
        System.out.println(payload);
        accountService.updateAccount(id, field, value);
        return ResponseEntity.ok().build();
    }
}
