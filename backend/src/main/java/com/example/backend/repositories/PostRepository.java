package com.example.backend.repositories;

import com.example.backend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY p.dateCreated DESC LIMIT :limit OFFSET :offset")
    List<Post> findAllByPageOrderByDateCreatedDesc(@Param("limit") int limit, @Param("offset") int offset);
}
