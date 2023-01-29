package com.blogapp.blogappiapi.repositories;

import com.blogapp.blogappiapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

}