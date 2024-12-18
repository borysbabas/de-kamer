package com.example.backend.services;

import com.example.backend.entities.Account;
import com.example.backend.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private static final List<String> ALLOWED_FIELDS = Arrays.asList("email","userName", "password", "dateOfBirth");

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public String hashPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public List<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account findById(Long id) {
        return this.accountRepository.findById(id).orElse(null);
    }

    public Account deleteById(Long id) {
        Account account = this.accountRepository.findById(id).orElse(null);
        if( account != null){
            this.accountRepository.deleteById(id);
        }
        return account;
    }
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
