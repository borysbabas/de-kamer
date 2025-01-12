package com.example.backend.controller;

import com.example.backend.DTO.AccountDTO;
import com.example.backend.authentication.AuthenticateJWToken;
import com.example.backend.config.ApiConfig;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Account;
import com.example.backend.entities.Post;
import com.example.backend.services.AccountService;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
public class AccountDetailsController {
    private final AccountService accountService;
    private final PostService postService;

    @Autowired
    public AccountDetailsController(AccountService accountService, PostService postService) {
        this.accountService = accountService;
        this.postService = postService;
    }

    @GetMapping(path = ApiPaths.ACCOUNT_DETAILS)
    public AccountDTO getAccountById(@PathVariable Long id) {
        return new AccountDTO(Objects.requireNonNull(accountService.findAccountById(id)
                .stream()
                .findFirst()
                .orElse(null)));
    }

    @DeleteMapping(path = ApiPaths.DELETE_ACCOUNT)
    public boolean deleteAccount(@RequestAttribute(ApiConfig.ACCOUNT_HEADER) Account account) {
        postService.deletePostsByAccountId(account.getId());
        return accountService.deleteAccountById(account.getId());
    }

    @PutMapping(path= ApiPaths.ACCOUNTDETAILS_SERVICE_UPDATE)
    public ResponseEntity<?> updateUserDetails(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader,@PathVariable(value = "id", required = true)Long id, @RequestBody Map<String, String> payload){
        String field = payload.get("field");
        String value = payload.get("value");
        System.out.println(payload);
        accountService.updateAccount(id, field, value);
        return ResponseEntity.ok().build();
    }
}
