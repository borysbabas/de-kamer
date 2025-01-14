package com.example.backend.controller;

import com.example.backend.DTO.AdminViewAccountDTO;
import com.example.backend.config.ApiConfig;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Account;
import com.example.backend.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    private final RequestMappingHandlerMapping handlerMapping;
    private final AccountService accountService;

    @Autowired
    public AdminController(RequestMappingHandlerMapping handlerMapping, AccountService accountService) {
        this.handlerMapping = handlerMapping;
        this.accountService = accountService;
    }

    @GetMapping(ApiPaths.ROUTES)
    public Object listRoutes() {
        return handlerMapping.getHandlerMethods()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().toString() + " => " + entry.getValue().getMethod().toString())
                .collect(Collectors.toList());
    }

    @GetMapping(ApiPaths.ACCOUNTS)
    public ResponseEntity<?> getAllAccounts(@RequestAttribute(ApiConfig.ACCOUNT_HEADER) Account account) {
        if (!Objects.equals(account.getRole(), "admin")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(accountService.getAllAccounts().stream().map(AdminViewAccountDTO::new), HttpStatus.OK);
    }

    @PutMapping(ApiPaths.BAN_ACCOUNT)
    public ResponseEntity<?> banAccount(@RequestAttribute(ApiConfig.ACCOUNT_HEADER) Account account, @PathVariable Long id) {
        if (!Objects.equals(account.getRole(), "admin")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Account accountToBan = accountService.getAccountById(id);
        if (accountToBan == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountToBan.setBanned(true);
        accountService.saveAccount(accountToBan);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}