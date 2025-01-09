package com.example.backend;

import com.example.backend.controller.PostController;
import com.example.backend.entities.Account;
import com.example.backend.entities.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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
        @BeforeAll
        public static void init() {
            Account mockAccount = new Account();
            mockAccount.setId(1L);
            mockAccount.setUserName("testUser");
            mockAccount.setEmail("username");
            mockAccount.setPassword("password");
            mockAccount.setRole("user");
            mockAccount.setDateOfBirth(new Date("01/01/2000"));


        }

        @Test
        public void addingPostWithValidToken() throws Exception {
            Post mockPost = new Post();
            mockPost.setPostId(1L);
            mockPost.setTitle("Test Post");
            mockPost.setDescription("This is a test post");
            mockPost.setAccount(null);

            mockMvc.perform(post("/posts/add")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer validToken")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockPost)))
                    .andExpect(status().isForbidden());

        }
    }
}
