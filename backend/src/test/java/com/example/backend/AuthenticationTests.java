package com.example.backend;

import com.example.backend.DTO.AccountDTO;
import com.example.backend.authentication.AuthenticateJWToken;
import com.example.backend.controller.AuthenticationController;
import com.example.backend.entities.Account;
import com.example.backend.repositories.AccountRepository;
import com.example.backend.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;



import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("authenticationTest")
public class AuthenticationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository = mock(AccountRepository.class);
    @Autowired
    AccountService  accountService = mock(AccountService.class);
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

    @Test
    void registerShouldReturnTrue() throws Exception{
        testAccount.setId(id);
        testAccount.setEmail(email);
        accountService.hashPassword(password);
        testAccount.setPassword(password);
        testAccount.setUserName(userName);
        testAccount.setRole(role);
        testAccount.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));
        assertThat(accountService).isNotNull();
        System.out.println(testAccount);
        mockMvc.perform(post("/authentication/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAccount)))
                .andDo(print())
                .andExpect(status().isCreated());
        accountService.saveAccount(testAccount);

    }
    @Test
    void authenticateAccount_shouldReturnToken()throws Exception{
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setEmail("test@example.com");
        mockAccount.setUserName(null);
        mockAccount.setDateOfBirth(null);
        mockAccount.setPassword("password");
        mockAccount.setRole("user");

        AccountDTO mockAccountDTO =new AccountDTO(mockAccount);
        ObjectNode signInInfo = objectMapper.createObjectNode();
        signInInfo.put("email", "test@example.com");
        signInInfo.put("password", "password");

        mockMvc.perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(signInInfo)))
                .andExpect(status().isAccepted())
                .andExpect(header().exists("Authorization"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("user"));


    }
    @Test
    void testFindByEmail_shouldReturnAccount(){
        accountService.findByEmail(email);
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
    void updateAccountTest(){
        String field = "email";
        String value = "johnsnow@hotmail.com";
        accountService.updateAccount(id, field, value);
    }
    @Test public void deleteAccountById(){
        accountService.findAccountById(id);
    }
    @Test
    public void findAccountById(){
        accountService.findAccountById(id);
    }
}
