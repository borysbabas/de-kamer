package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Post;
import com.example.backend.models.PostModel;
import com.example.backend.services.AccountService;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping
public class PostController {

    private final PostService postService;
    private final AccountService accountService;

    @Autowired
    public PostController(PostService postService, AccountService accountService) {
        this.postService = postService;
        this.accountService = accountService;
    }

    @GetMapping(path = ApiPaths.POST_SERVICE)
    public List<PostModel> getAllPosts() {
        return postService.findAllPosts();
    }

    @PostMapping(path = ApiPaths.POST_SERVICE)
    public ResponseEntity<String> addPost(@RequestBody Post post, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String[] tokenParts = token.split("\\.");
        String payload = new String(decoder.decode(tokenParts[1]));
        long userId = Long.parseLong(payload.split(",")[1].split(":")[1].replace("\"", ""));

        post.setId(accountService.findById(userId));
        if (post.getId() == null) {
            return new ResponseEntity<>("User ID not found", HttpStatus.FORBIDDEN);
        }

        postService.addPost(post);
        return new ResponseEntity<>("Post added successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = ApiPaths.FIND_POST_BY_ID)
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
    }
}