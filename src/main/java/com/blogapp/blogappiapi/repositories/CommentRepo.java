package com.blogapp.blogappiapi.repositories;

import com.blogapp.blogappiapi.entities.Comment;
import com.blogapp.blogappiapi.entities.Post;
import com.blogapp.blogappiapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
    List<Comment> findByUser(User user);
    List<Comment> findByPost(Post post);
}