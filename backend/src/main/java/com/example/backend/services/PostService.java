package com.example.backend.services;

import com.example.backend.entities.Post;
import com.example.backend.models.PostModel;
import com.example.backend.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostModel> findAllPosts() {
        return postRepository.findAll().stream().map(PostModel::new).toList();
    }

    public void addPost(Post post) {
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public long getPostCount() {
        return postRepository.count();
    }

    public List<PostModel> getRecentPosts(int postsPerPage, int currentPage) {
        return postRepository.findAllByPageOrderByDateCreatedDesc(postsPerPage, (currentPage - 1) * postsPerPage).stream().map(PostModel::new).toList();
    }

    public PostModel getPostById(long postId) {
        return new PostModel(postRepository.getPostByPostId(postId));
    }
}