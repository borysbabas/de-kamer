package com.example.backend.services;

import com.example.backend.entities.Account;
import com.example.backend.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Class that servers a service for managing and controlling account entities.
 *
 * This class contains logic for creating, updating , retrieving and deleting accounts.
 * This class uses the AccountRepository to perform databases actions.
 */
@Service
@Transactional
public class AccountService {
    //initializes the account repository.
    private final AccountRepository accountRepository;
    //helper field to only allow certain values.
    private static final List<String> ALLOWED_FIELDS = Arrays.asList("email","userName", "password", "dateOfBirth");

    /**
     * Constructs the service with the specified  repository.
     * @param accountRepository the actual repository to manage {@link Account} entities.
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
//method to save an account entity
    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    /**
     * Hashes a plain text password using BCrypt algorithm.
     * @param password password as plain text
     * @return the hashed password
     */
    public String hashPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
//helper method to locate the account corresponding to the parameterized email.
    public List<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account findById(Long id) {
        return this.accountRepository.findById(id).orElse(null);
    }

    /**
     * Deletes an account
     * @param id the unique identifier of the account to delete.
     * @return the deleted account entity , or null if there was no account found.
     *
     */
    public Account deleteById(Long id) {
        Account account = this.accountRepository.findById(id).orElse(null);
        if( account != null){
            this.accountRepository.deleteById(id);
        }
        return account;
    }

    /**
     * Updates a specific  field of an account by using a switch case to identify which field to update.
     *
     * @param id the ID of the account to update
     * @param field the value that must be updated changes accordingly.
     * @param value the new value for the field.
     * @throws IllegalArgumentException if the field is not allowed or the value is invalid.
     */
    public void updateAccount(Long id, String field, String value){
        if(!ALLOWED_FIELDS.contains(field)){
            throw new IllegalArgumentException("invalid field"+field);

        }
        Account account = accountRepository.findById(id).orElseThrow(null);
        switch (field){
            case "email":
                account.setEmail(value);
                break;
            case "userName":
                account.setUserName(value);
                break;
            case "password":
                account.setPassword(hashPassword(value));
                break;
            case "dateOfBirth":
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(value);
                    account.setDateOfBirth(date);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Ongeldige datumformat: " + value, e);
                }
        }
        accountRepository.save(account);
    }

    /**
     * Method that deletes an account according to what Unique identifier is has been given.
     * @param id the id of the account entity that must be deleted in this case.
     * @return boolean value of true or false  {@code true} if account was successfully deleted , {@code false} if account could not be deleted or altered.
     */
    public boolean deleteAccountById(Long id){
        Account account = this.accountRepository.findById(id).orElse(null);
        if (account!=null){
            this.accountRepository.deleteAccountById(id);
            return true;
        }
        return false;
    }
    public List<Account>findAccountById(Long id){return accountRepository.findAccountById(id);}
}
