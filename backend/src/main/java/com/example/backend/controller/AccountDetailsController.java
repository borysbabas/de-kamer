package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Account;
import com.example.backend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountDetailsController {
    private final AccountService accountService;
    @Autowired
    public AccountDetailsController(AccountService accountService){
        this.accountService=accountService;
    }
    @GetMapping(path= ApiPaths.ACCOUNTDETAILS_SERVICE_GET_USERBYID)
    public Account getAccountById(@PathVariable Long id){
        return accountService.findAccountById(id)
                .stream()
                .findFirst()
                .orElse(null);
    }

}
