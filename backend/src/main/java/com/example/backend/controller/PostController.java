package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import com.example.backend.entities.Post;
import com.example.backend.models.PostModel;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public void addPost(@RequestBody Post post) {
        postService.addPost(post);
    }

    @DeleteMapping(path = "/{postId}")
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
}