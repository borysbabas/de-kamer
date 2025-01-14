package com.example.backend.models;

import com.example.backend.DTO.AccountDTO;
import com.example.backend.entities.Account;
import com.example.backend.entities.Post;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostModel {
    private Long id;
    private String title;
    private String description;
    private AccountDTO account;
    private String dateCreated;

    public PostModel(Post post) {
        this.id = post.getPostId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.dateCreated = post.getDateCreated().toString();
        if (post.getAccount() != null) {
            this.account = new AccountDTO(post.getAccount());
        } else {
            this.account = null;
        }
    }
}