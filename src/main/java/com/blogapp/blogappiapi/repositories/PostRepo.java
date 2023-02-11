package com.blogapp.blogappiapi.repositories;

import com.blogapp.blogappiapi.entities.Category;
import com.blogapp.blogappiapi.entities.Post;
import com.blogapp.blogappiapi.entities.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);


    List<Post> findByTitleContaining(String title);
//    @Query("select p from Post p where p.title like %:keyword%")
//    List<Post> searchByKey(@Param("key") String key,@Param("keyword") String keyword);
}