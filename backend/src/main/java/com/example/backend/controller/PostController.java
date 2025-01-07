package com.example.backend.controller;

import com.example.backend.authentication.AuthenticateJWToken;
import com.example.backend.authentication.JWToken;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Post;
import com.example.backend.models.PostModel;
import com.example.backend.services.AccountService;
import com.example.backend.services.PostService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PostController {

    private final PostService postService;
    private final AccountService accountService;
    private final AuthenticateJWToken authenticateJWToken;

    @Autowired
    public PostController(PostService postService, AccountService accountService, AuthenticateJWToken authenticateJWToken) {
        this.postService = postService;
        this.accountService = accountService;
        this.authenticateJWToken = authenticateJWToken;
    }

    @GetMapping(path = ApiPaths.POST_SERVICE)
    public List<PostModel> getAllPosts() {
        return postService.findAllPosts();
    }

    @PostMapping(path = ApiPaths.ADD_POST)
    public ResponseEntity<String> addPost(@RequestBody Post post, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            if (!authenticateJWToken.verifyToken(token)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (JwtException e) {
            System.out.println("Token is invalid!\n" + e.getMessage());
        }

        JWToken jwt = authenticateJWToken.getJWToken(token);

        post.setId(accountService.findById(jwt.getAccountId()));
        if (post.getId() == null) {
            return new ResponseEntity<>("User ID not found", HttpStatus.FORBIDDEN);
        }

        postService.addPost(post);
        return new ResponseEntity<>("Post added successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = ApiPaths.DELETE_POST_BY_ID)
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
    }

    @GetMapping(path = ApiPaths.POST_SERVICE_COUNT)
    public long getPostCount() {
        return postService.getPostCount();
    }

    @GetMapping(path = ApiPaths.POST_SERVICE_RECENT + "/{postsPerPage}/{currentPage}")
    public List<PostModel> getRecentPosts(@PathVariable("postsPerPage") Integer postsPerPage, @PathVariable("currentPage") Integer currentPage) {
        return postService.getRecentPosts(postsPerPage, currentPage);
    }

    @GetMapping(path = ApiPaths.GET_POST_BY_ID)
    public PostModel getPostById(@PathVariable("postId") Long postId) {
        return postService.getPostById(postId);
    }
}