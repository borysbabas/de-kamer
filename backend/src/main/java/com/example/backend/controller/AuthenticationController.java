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

    /**
     * Constructor that initializes the AuthenticationController Object
     * @param accountService the service that handles business logic for the account repository
     * @param apiConfig the configuration file that holds the endpoints as final strings
     */
    @Autowired
    public AuthenticationController(AccountService accountService, ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.accountService = accountService;
    }

    /**
     * Method that is called when a user signs into the application
     * @param account hold the values needed for the login to be successful.
     * @return ResponseEntity accepted when credentials match.
     */
    @PostMapping(path = ApiPaths.AUTHENTICATION_SERVICE_LOGIN)
    public ResponseEntity<AccountDTO> authenticateAccount(@RequestBody Account account) {
        String email = account.getEmail();
        List<Account> accounts = accountService.findByEmail(email);

        AccountDTO foundAccount = new AccountDTO(accounts.size() == 1
                ? accounts.get(0)
                : new Account());

        // Password comparison using BCrypt
        if (foundAccount.getId() == null || !org.springframework.security.crypto.bcrypt.BCrypt.checkpw(account.getPassword(), accounts.get(0).getPassword())) {
            throw new NotAcceptableException("Cannot authenticate account with email: " + email);
        }

        // Create a new JWT token
        JWToken jwToken = new JWToken(foundAccount.getUserName(), foundAccount.getId(), foundAccount.getRole());
        String tokenString = jwToken.encode(
                this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity()
        );

        foundAccount.setJwtToken(tokenString);
        return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString).body(foundAccount);
    }

    /**
     * Method that handles the registration process of the client
     * @param account the Account object that holds te values needed to save the account.
     * @return ResponseEntity conflict if the email has already been taken and created of credentials are valid.
     */
    @PostMapping(path = ApiPaths.AUTHENTICATION_SERVICE_REGISTER)
    public ResponseEntity<Account> saveAccount(@RequestBody Account account) {
        String email = account.getEmail();
        String userName = account.getUserName();
        String password = account.getPassword();

        if (email == null || userName == null || password == null) {
            throw new NotAcceptableException("Missing required fields");
        }

        // Check if email is already taken
        if (!accountService.findByEmail(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        // Create the account entity
        account.setRole("user");
        account.setPassword(accountService.hashPassword(password));

        accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    /**
     * Helper method that handles logic related to deleting a singular account from the database.
     * @param id the Unique identifier that tells us which account to perform this method on
     * @param jwtInfo the Token that holds checks the user's authorization level.
     * @return Custom ResourceNotFoundException when an account can't be deleted, otherwise returns an ResponseEntity object OK too inform the client of the succes of the method.
     *
     */
    @DeleteMapping(path = ApiPaths.AUTHENTICATION_SERVICE_DELETE)
    public ResponseEntity<Account> deleteOneAccount(@PathVariable long id, @RequestAttribute(name = JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtInfo) {
        if (jwtInfo == null || !jwtInfo.isAdmin()) {
            throw new UnAuthorizedException("Admin role is required to remove an account");
        }

        Account account = accountService.deleteById(id);
        if (account == null) {
            throw new ResourceNotFoundException("Cannot delete an account with id: " + id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
}
