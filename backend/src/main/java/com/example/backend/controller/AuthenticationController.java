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
/**
 * class that handles all endpoints related to authenticating user e.g. sign in sing out and a delete helper method.
 */
public class AuthenticationController {
    private final ApiConfig apiConfig;
    private final AccountService accountService;

    /**
     * The constructor that initializes the authentication controller object
     * @param accountService service layer that manages account related functionality
     * @param apiConfig configuration file that handles important data for jwt and handles cors.
     */
    @Autowired
    public AuthenticationController(AccountService accountService, ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.accountService=accountService;
    }

    /**
     * Method that signs the user into their account if it is found and signs a new jwt token if checks pass.
     * @param signInInfo the values send from the frontend into this endpoint
     * @return ResponseEntity accepted if account is not null and verifypassword returns true.
     */
    @PostMapping(path=ApiPaths.AUTHENTICATION_SERVICE_LOGIN)
    public ResponseEntity<AccountDTO> authenticateAccount(@RequestBody ObjectNode signInInfo) {
        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        List<Account> accounts = accountService.findByEmail(email);

        AccountDTO account = new AccountDTO(accounts.size() == 1
                ? accounts.get(0)
                : new Account());
// checks if account isn't null and if the password is correct.
        if (account.getId() == null || !account.verifyPassword(password)) {
            throw new NotAcceptableException("cannot authenticate account with email:"+ email);
        }
        // creates new jwtoken
        JWToken jwToken = new JWToken(account.getUserName(), account.getId(), account.getRole());
        String tokenString = jwToken.encode(
                this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity()
        );

        account.setJwtToken(tokenString);
        return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer" + tokenString).body(account);
    }

    /**
     * Method that creates a new account entity with the given signUpInfo
     * @param signUpInfo the values send from the frontend that specify the users credentials
     * @return ResponseEntity.status created if email is valid otherwise returns conflict.
     * @throws NotAcceptableException if values are missing in @param.
     * @throws ResponseEntity Bad request if there is a different error.
     */
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
// try to format dateofbirth values from client to align with database structure.
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateOfBirth = simpleDateFormat.parse(dateOfBirthString);

        } catch(Exception exception){
            log.error("e: ", exception);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
// check if email is already taken.
        Account account = new Account();
        if (!accountService.findByEmail(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
// creates actual account entity.
        account.setEmail(email);
        account.setRole("user");
        account.setUserName(userName);
        account.setPassword(accountService.hashPassword(password));
        account.setDateOfBirth(dateOfBirth);

        accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    /**
     * Method that deletes a singular account by its id.
     * Note: this method can only be executed if there is a valid token present.
     * @param id the client's id to identify the current account entity.
     * @param jwtInfo the token that is sent through the HTTP header.
     * @return false or true if account is successfully deleted or not.
     * @throws ResourceNotFoundException if account is null
     * @throws UnAuthorizedException if the client doesn't have the required role.
     */
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
