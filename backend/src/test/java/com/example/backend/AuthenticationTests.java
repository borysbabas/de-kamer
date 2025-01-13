package com.example.backend;

import com.example.backend.DTO.AccountDTO;
import com.example.backend.authentication.AuthenticateJWToken;
import com.example.backend.controller.AuthenticationController;
import com.example.backend.entities.Account;
import com.example.backend.repositories.AccountRepository;
import com.example.backend.services.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService  accountService;
    @Autowired
    private AuthenticateJWToken authenticateJWToken;
    private final Account testAccount = new Account();


    private final Long id=1L;
    private final String email= "JohnDoe@gmail.com";
    private final String password="Strongpassword";
    private final String role = "user";
    private final String userName= "gebruikersnaam";

    @Autowired
    private AuthenticationController authenticationController;

    @Test
    public void  contextLoad(){
        assertThat(authenticationController).isNotNull();
        assertThat(accountService).isNotNull();
        assertThat(accountRepository).isNotNull();
        assertThat(authenticateJWToken).isNotNull();
    }



    @Nested
    public class UserAccountTests {
        private String token;

        @BeforeEach
        public void init() throws Exception {
            Account mockAccount = new Account();
            mockAccount.setId(1L);
            mockAccount.setUserName(userName);
            mockAccount.setEmail(email);
            mockAccount.setPassword(password);
            mockAccount.setRole(role);

            System.out.println(mockAccount);

            mockMvc.perform(post("/authentication/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(mockAccount)))
                    .andExpect(status().isCreated());
        }

        @Test
        void authenticateAccount_shouldReturnToken() throws Exception{
            Account mockAccount = new Account();
            mockAccount.setId(1L);
            mockAccount.setEmail(email);
            mockAccount.setUserName(userName);
            mockAccount.setPassword(password);
            mockAccount.setRole(role);

            ResultActions result = mockMvc.perform(post("/authentication/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockAccount)))
                    .andExpect(status().isAccepted());

            token = result.andReturn().getResponse().getHeader(HttpHeaders.AUTHORIZATION);
        }
    }


    @Test
    void testFindByEmail_shouldReturnAccount(){

        assertThat(accountService.findByEmail(email)).isNotNull();
    }
    @Test
    void findByIdTest(){
        accountService.findById(id);
    }
    @Test
    void deleteByIdTest(){
        accountService.deleteById(id);
    }
    @Test
    void updateAccountTest() throws Exception {
        String field = "email";
        String value = "johnsnow@hotmail.com";
        accountService.updateAccount(id, field, value);
    }
    @Test
    public void deleteAccountById() {
        accountService.findAccountById(id);
    }

    @Test
    public void findAccountById() {
        accountService.findAccountById(id);
    }
}
