package com.example.backend;

import com.example.backend.controller.PostController;
import com.example.backend.entities.Account;
import com.example.backend.entities.Post;
import com.example.backend.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostTest {
    @Autowired
    private PostController postController;

    @Autowired
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final int TEST_POST_AMOUNT = 10;
    private final Account mockAccount = new Account();

    public PostTest() {
        mockAccount.setId(1L);
        mockAccount.setUserName("testUser");
        mockAccount.setEmail("username");
        mockAccount.setPassword("password");
        mockAccount.setRole("user");
    }

    @Test
    public void contextLoads() {
        assertThat(postController).isNotNull();
        assertThat(postService).isNotNull();
    }

    @Nested
    class GeneralPostTests {
        @BeforeEach()
        public void init() {
            for (int i = 1; i < TEST_POST_AMOUNT + 1; i++) {
                Post mockPost = new Post();
                mockPost.setPostId((long) i);
                mockPost.setTitle("Test Post " + i);
                mockPost.setDescription("This is a test post");
                mockPost.setAccount(mockAccount);

                postService.addPost(mockPost);
            }
        }

        @Test
        public void addingPostWithInvalidToken() throws Exception {
            Post mockPost = new Post();
            mockPost.setPostId(1L);
            mockPost.setTitle("Test Post");
            mockPost.setDescription("This is a test post");
            mockPost.setAccount(null);

            mockMvc.perform(post("/account/posts/add")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer invalidToken")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(mockPost)))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        public void addingPostWithNoToken() throws Exception {
            Post mockPost = new Post();
            mockPost.setPostId(1L);
            mockPost.setTitle("Test Post");
            mockPost.setDescription("This is a test post");
            mockPost.setAccount(null);

            mockMvc.perform(post("/account/posts/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(mockPost)))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        public void checkPostCount() throws Exception {
            mockMvc.perform(get("/posts/count"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(Long.parseLong(content)).isEqualTo(TEST_POST_AMOUNT);
                    });
        }

        @Test
        public void getPostById() throws Exception {
            mockMvc.perform(get("/posts/1"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(content).contains("Test Post 1");
                    });
        }

        @Test
        public void getRecentPosts() throws Exception {
            mockMvc.perform(get("/posts/recent/8/1"))
                    .andExpect(status().isOk())
                    .andExpect(result -> {
                        String content = result.getResponse().getContentAsString();
                        assertThat(content).contains("\"Test Post 10\"");
                        assertThat(content).doesNotContain("\"Test Post 1\"");
                    });
        }
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
            mockPost.setAccount(mockAccount);

            mockMvc.perform(post("/account/posts/add")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockPost)))
                    .andExpect(status().isOk());

        }
    }
}
