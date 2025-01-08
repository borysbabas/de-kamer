package com.example.backend;

import com.example.backend.controller.AuthenticationController;
import com.example.backend.repositories.AccountRepository;
import com.example.backend.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class AuthenticationTests {
    @BeforeEach
    void setup(){
        AccountRepository accountRepository = mock(AccountRepository.class);
    }
    @Test
    void loginShouldReturnTrue(){

    }
}
