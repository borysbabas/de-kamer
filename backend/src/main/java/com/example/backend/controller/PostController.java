package com.example.backend.controller;

import com.example.backend.config.ApiConfig;
import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Account;
import com.example.backend.entities.Post;
import com.example.backend.models.PostModel;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = ApiPaths.POST_SERVICE)
    public List<PostModel> getAllPosts() {
        return postService.findAllPosts();
    }

    @PostMapping(path = ApiPaths.ADD_POST)
    public ResponseEntity<String> addPost(@RequestBody Post post, @RequestAttribute(ApiConfig.ACCOUNT_HEADER) Account account) {

        post.setAccount(account);
        if (post.getAccount() == null) {
            return new ResponseEntity<>("User ID not found", HttpStatus.FORBIDDEN);
        }

        postService.addPost(post);
        return new ResponseEntity<>("Post added successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = ApiPaths.DELETE_POST_BY_ID)
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId, @RequestAttribute(ApiConfig.ACCOUNT_HEADER) Account account) {
        PostModel post = postService.getPostById(postId);
        if (post == null) {
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(account.getId(), post.getAccount().getId())) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        if ((Objects.requireNonNull(e.getParameter().getMethod())).getName().equals("getPostById")) {
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<String> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        return new ResponseEntity<>("Missing request header", HttpStatus.FORBIDDEN);
    }
}