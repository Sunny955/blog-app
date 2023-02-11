package com.blogapp.blogappiapi.repositories;

import com.blogapp.blogappiapi.entities.Category;
import com.blogapp.blogappiapi.entities.Post;
import com.blogapp.blogappiapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

//    @Query("select p from Post p where p.title like :key")
//    List<Post> searchByTitle(@Params("key") String title);
    List<Post> findByTitleContaining(String title);
}