package com.example.backend.services;

import com.example.backend.entities.Account;
import com.example.backend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

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

    public List<Account> findByQuery(String email) {
        return accountRepository.findByQuery(email);
    }

    public Account deleteById(Long id) {
        Account account = this.accountRepository.findById(id).orElse(null);
        if( account != null){
            this.accountRepository.deleteById(id);
        }
        return account;
    }
}
