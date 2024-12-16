package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Post;
import com.example.backend.models.PostModel;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(path = ApiPaths.POST_SERVICE)
    public ResponseEntity<String> addPost(@RequestBody Post post) {
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