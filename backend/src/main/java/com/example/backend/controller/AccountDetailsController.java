package com.example.backend.controller;

import com.example.backend.authentication.JWToken;
import com.example.backend.config.ApiConfig;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Account;
import com.example.backend.services.AccountService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.List;
import java.util.Objects;

@RestController
public class AccountDetailsController {
    private final AccountService accountService;
    private final ApiConfig apiConfig;

    @Autowired
    public AccountDetailsController(AccountService accountService, ApiConfig apiConfig) {
        this.accountService = accountService;
        this.apiConfig = apiConfig;
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
            String issuer = this.apiConfig.getIssuer();
            String passPhrase = this.apiConfig.getPassphrase();
            JWToken jwt = JWToken.decode(token, issuer, passPhrase);
            if (Objects.equals(jwt.getAccountId(), id)) {
                return accountService.deleteAccountById(id);
            }

        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired" + e.getMessage());

        } catch (MalformedJwtException e) {
            System.out.println("Token is invalid!" + e.getMessage());

        }
        return false;

    }


}
