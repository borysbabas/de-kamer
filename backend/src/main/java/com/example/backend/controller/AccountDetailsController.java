package com.example.backend.controller;

import com.example.backend.authentication.AuthenticateJWToken;
import com.example.backend.authentication.JWToken;
import com.example.backend.config.ApiConfig;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Account;
import com.example.backend.services.AccountService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * RestController that handles endpoints related to the account details page.
 * Provides functionality for retrieving, updating and deleting accounts.
 */
@RestController
public class AccountDetailsController {
    // private fields that instantiate the accountservice class and authentication class.
    private final AccountService accountService;
    private final AuthenticateJWToken authenticateJWToken;

    /**
     * Constructor that initializes the controller object.
     * @param accountService service for handling accounts.
     * @param authenticateJWToken utility for validating jwt tokens.
     */
    @Autowired
    public AccountDetailsController(AccountService accountService, AuthenticateJWToken authenticateJWToken) {
        this.accountService = accountService;
        this.authenticateJWToken = authenticateJWToken;
    }

    /**
     * Retrieves a single account by its ID.
     * @param authorizationHeader the header containing the jwt
     * @param id id of the account to retrieve.
     * @return the account if found and authorized , or null.
     * catches a jwtException when an error is encountered in the jwt.
     */
    @GetMapping(path = ApiPaths.ACCOUNTDETAILS_SERVICE_GET_USERBYID)
    public Account getAccountById(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable Long id) {
        try {
            if (authenticateJWToken.verifyToken(authorizationHeader)) {
                return accountService.findAccountById(id)
                        .stream()
                        .findFirst()
                        .orElse(null);
            }
        } catch (JwtException e) {
            System.out.println("Token is invalid!\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Method to delete a single account by the corresponding id
     * @param authorizationHeader the header that contains the jwt
     * @param id the provided id of the account that needs to be deleted.
     * @return a boolean value. if the deletion is successful it returns true, false if not.
     */
    @DeleteMapping(path = ApiPaths.ACCOUNTDETAILS_SERVICE_DELETE_USERBYID)
    public boolean deleteAccountById(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable(value = "id", required = true) Long id) {
        try {
            if (authenticateJWToken.verifyToken(authorizationHeader)) {
                return accountService.deleteAccountById(id);
            }

        } catch (JwtException e) {
            System.out.println("Token is invalid!\n" + e.getMessage());
        }
        return false;

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
        try {
            if (authenticateJWToken.verifyToken(authorizationHeader)) {
                accountService.updateAccount(id, field, value);
                return ResponseEntity.ok("Account successfully updated");
            }

        } catch (JwtException e) {
            System.out.println("Token is invalid!\n" + e.getMessage());
        }

        return ResponseEntity.status(500).body("Interne serverfout");
    }
}
