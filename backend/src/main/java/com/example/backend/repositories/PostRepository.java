package com.example.backend.repositories;

import com.example.backend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Custom query method to retrieve limited number of most recent posts from a given position in the database
     *
     * @param limit Number of posts to be retrieved from the database
     * @param offset Position in the database table from where to start getting the posts
     * @return List of limited number of recent posts from given position in the database table
     */
    @Query("SELECT p FROM Post p ORDER BY p.dateCreated DESC LIMIT :limit OFFSET :offset")
    List<Post> findAllByPageOrderByDateCreatedDesc(@Param("limit") int limit, @Param("offset") int offset);
}
