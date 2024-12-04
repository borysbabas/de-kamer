package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Post;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ApiPaths.POST_SERVICE)
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }

    @PostMapping
    public void addPost(@RequestBody Post post) {
        postService.addPost(post);
    }

    @DeleteMapping(path = "/{postId}")
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
    }
}