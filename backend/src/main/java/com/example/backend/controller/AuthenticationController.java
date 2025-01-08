package com.example.backend.controller;

import com.example.backend.DTO.AccountDTO;
import com.example.backend.authentication.JWToken;
import com.example.backend.config.ApiConfig;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Account;
import com.example.backend.services.AccountService;
import com.example.backend.utils.NotAcceptableException;
import com.example.backend.utils.ResourceNotFoundException;
import com.example.backend.utils.UnAuthorizedException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@RestController
public class AuthenticationController {
    private final ApiConfig apiConfig;
    private final AccountService accountService;

    @Autowired
    public AuthenticationController(AccountService accountService, ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.accountService=accountService;
    }

    @PostMapping(path=ApiPaths.AUTHENTICATION_SERVICE_LOGIN)
    public ResponseEntity<AccountDTO> authenticateAccount(@RequestBody ObjectNode signInInfo) {
        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        List<Account> accounts = accountService.findByEmail(email);

        AccountDTO account = new AccountDTO(accounts.size() == 1
                ? accounts.get(0)
                : new Account());

        if (account.getId() == null || !org.springframework.security.crypto.bcrypt.BCrypt.checkpw(password, accounts.get(0).getPassword())) {
            throw new NotAcceptableException("cannot authenticate account with email:"+ email);
        }
        JWToken jwToken = new JWToken(account.getUserName(), account.getId(), account.getRole());
        String tokenString = jwToken.encode(
                this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity()
        );

        account.setJwtToken(tokenString);
        return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer" + tokenString).body(account);
    }

    @PostMapping(path=ApiPaths.AUTHENTICATION_SERVICE_REGISTER)
    public ResponseEntity<Account> saveAccount(@RequestBody ObjectNode signUpInfo) {
        String email = signUpInfo.get("email").asText();
        String userName = signUpInfo.get("username").asText();
        String password = signUpInfo.get("password").asText();
        String dateOfBirthString= signUpInfo.get("dateOfBirth").asText();
        if(email==null || userName == null || password == null || dateOfBirthString == null){
            throw new NotAcceptableException("missing required fields");
        }
        Date dateOfBirth;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateOfBirth = simpleDateFormat.parse(dateOfBirthString);

        } catch(Exception exception){
            log.error("e: ", exception);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Account account = new Account();
        if (!accountService.findByEmail(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        account.setEmail(email);
        account.setRole("user");
        account.setUserName(userName);
        account.setPassword(accountService.hashPassword(password));
        account.setDateOfBirth(dateOfBirth);

        accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @DeleteMapping(path= ApiPaths.AUTHENTICATION_SERVICE_DELETE)
    public Account deleteOneAccount(@PathVariable() long id, @RequestAttribute(name = JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtInfo) {
        if(jwtInfo == null ||!jwtInfo.isAdmin()) {
            throw new UnAuthorizedException("Admin role is required to remove an account");
        }

        Account account= this.accountService.deleteById(id);
        if(account==null){
            throw new ResourceNotFoundException("Cannot delete an account with id");
        }
        return account;
    }
}
