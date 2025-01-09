package com.example.backend;

import com.example.backend.controller.PostController;
import com.example.backend.entities.Account;
import com.example.backend.entities.Post;
import com.example.backend.services.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostTest {
    @Autowired
    private PostController postController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() {
        assertThat(postController).isNotNull();
    }

    @Test
    public void addingPostWithInvalidToken() throws Exception {
        Post mockPost = new Post();
        mockPost.setPostId(1L);
        mockPost.setTitle("Test Post");
        mockPost.setDescription("This is a test post");
        mockPost.setAccount(null);

        mockMvc.perform(post("/posts/add")
                .header(HttpHeaders.AUTHORIZATION, "Bearer invalidToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockPost)))
                .andExpect(status().isForbidden());

    }

    @Nested
    class UserAccountTests {

        private String token;

        @BeforeEach
        public void init() throws Exception {
            Account mockAccount = new Account();
            mockAccount.setId(1L);
            mockAccount.setUserName("testUser");
            mockAccount.setEmail("username");
            mockAccount.setPassword("password");
            mockAccount.setRole("user");
            mockAccount.setDateOfBirth(new Date("01/01/2000"));

            mockMvc.perform(post("/authentication/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockAccount)))
                    .andExpect(status().isCreated());

            ResultActions result = mockMvc.perform(post("/authentication/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockAccount)))
                    .andExpect(status().isAccepted());

            token = result.andReturn().getResponse().getHeader(HttpHeaders.AUTHORIZATION);

            assertThat(token).isNotNull();
        }

        @Test
        public void addingPostWithValidToken() throws Exception {
            Post mockPost = new Post();
            mockPost.setPostId(1L);
            mockPost.setTitle("Test Post");
            mockPost.setDescription("This is a test post");
            mockPost.setAccount(null);

            mockMvc.perform(post("/posts/add")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockPost)))
                    .andExpect(status().isOk());

        }
    }
}
