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

@RestController
public class AccountDetailsController {
    private final AccountService accountService;
    private final AuthenticateJWToken authenticateJWToken;

    @Autowired
    public AccountDetailsController(AccountService accountService, AuthenticateJWToken authenticateJWToken) {
        this.accountService = accountService;
        this.authenticateJWToken = authenticateJWToken;
    }

    @GetMapping(path = ApiPaths.ACCOUNTDETAILS_SERVICE_GET_USERBYID)
    public Account getAccountById(@PathVariable Long id) {
        return accountService.findAccountById(id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @DeleteMapping(path = ApiPaths.ACCOUNTDETAILS_SERVICE_DELETE_USERBYID)
    public boolean deleteAccountById(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authorizationHeader, @PathVariable(value = "id", required = true) Long id) {
        try {
            String token = authorizationHeader.substring(7);

            if (authenticateJWToken.verifyToken(token, id)) {
                return accountService.deleteAccountById(id);
            }

        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired" + e.getMessage());

        } catch (SignatureException e) {
            System.out.println("Token is invalid!" + e.getMessage());
        }
        return false;

    }
    @PutMapping(path= ApiPaths.ACCOUNTDETAILS_SERVICE_UPDATE)
    public ResponseEntity<?> updateUserDetails(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authorizationHeader,@PathVariable(value = "id", required = true)Long id, @RequestBody Map<String, String> payload){
        String field = payload.get("field");
        String value = payload.get("value");
        try {
            String token = authorizationHeader.substring(7);

            if (authenticateJWToken.verifyToken(token, id)) {
                accountService.updateAccount(id, field, value);
                return ResponseEntity.ok("Account successfully updated");
            }

        } catch (SignatureException e) {
            System.out.println("Token is invalid!" + e.getMessage());
        }

        return ResponseEntity.status(500).body("Interne serverfout");
    }
}
