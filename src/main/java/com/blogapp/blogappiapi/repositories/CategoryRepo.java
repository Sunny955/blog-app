package com.blogapp.blogappiapi.repositories;

import com.blogapp.blogappiapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}