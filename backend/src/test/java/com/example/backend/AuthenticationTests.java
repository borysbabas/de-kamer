package com.example.backend;

import com.example.backend.authentication.AuthenticateJWToken;
import com.example.backend.controller.AuthenticationController;
import com.example.backend.entities.Account;
import com.example.backend.repositories.AccountRepository;
import com.example.backend.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    public AuthenticationTests() {
        testAccount.setId(id);
        testAccount.setEmail(email);
        testAccount.setPassword(password);
        testAccount.setRole(role);
        testAccount.setUserName(userName);
    }

    @Test
    public void  contextLoad(){
        assertThat(authenticationController).isNotNull();
        assertThat(accountService).isNotNull();
        assertThat(accountRepository).isNotNull();
        assertThat(authenticateJWToken).isNotNull();
    }



    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class UserAccountTests {
        private String token;

        @BeforeAll
        public void setup() throws Exception {
            mockMvc.perform(post("/authentication/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testAccount)))
                    .andExpect(status().isCreated());
        }

        @BeforeEach
        public void init() throws Exception {
            ResultActions result = mockMvc.perform(post("/authentication/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testAccount)))
                    .andExpect(status().isAccepted());

            token = result.andReturn().getResponse().getHeader(HttpHeaders.AUTHORIZATION);
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
        }
        @Test
        void updateAccountTest() throws Exception {
            Map testMap = new HashMap();
            testMap.put("field", "userName");
            testMap.put("value", "newUserName");
            mockMvc.perform(put("/accountdetails/update/"+id).contentType(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, token)
                    .content(objectMapper.writeValueAsString(testMap))).andExpect(status().isOk());
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
    public void deleteAccountById() {
        accountService.deleteAccountById(id);
    }

    @Test
    public void findAccountById() {
        accountService.findAccountById(id);
    }
}
