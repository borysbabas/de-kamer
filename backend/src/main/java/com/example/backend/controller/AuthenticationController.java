package com.example.backend.controller;

import com.example.backend.authentication.JWToken;
import com.example.backend.config.ApiConfig;
import com.example.backend.entities.Account;
import com.example.backend.repositories.AccountRepository;
import com.example.backend.services.AccountService;
import com.example.backend.utils.NotAcceptableException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Configuration
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    ApiConfig apiConfig;
    private final AccountService accountService;

    @Autowired
    public AuthenticationController(AccountService accountService){
        this.accountService=accountService;
    }
    @PostMapping(path="/login")
    public ResponseEntity<Account> authenticateAccount(@RequestBody ObjectNode signInInfo){
        String email = signInInfo.get("email").asText();
        String password =signInInfo.get("password").asText();
        List<Account> accounts= accountService.findByQuery(email);
        Account account = accounts.size() == 1? accounts.get(0):null;
        if (account==null || !account.verifyPassword(password)){
            throw new NotAcceptableException("cannot authenitcate account with email:"+ email);
        }
        JWToken jwToken = new JWToken(account.getUserName(), account.getId(), account.getRole());
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(), this.apiConfig.getPassphrase(), this.apiConfig.getTokenDurationOfValidity());
        return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer"+tokenString).body(account);
    }
    @PostMapping(path="/register")
    public ResponseEntity<Account> saveAccount(@RequestBody ObjectNode signUpInfo){
        String email = signUpInfo.get("email").asText();
        String password = signUpInfo.get("password").asText();
        Account account = new Account();
        if(!accountService.findByQuery(email).isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        account.setEmail(email);
        account.setRole("user");
        account.setPassword(accountService.hashPassword(password));

        accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);



    }

}
