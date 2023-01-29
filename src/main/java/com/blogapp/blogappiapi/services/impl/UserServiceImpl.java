package com.blogapp.blogappiapi.services.impl;

import com.blogapp.blogappiapi.entities.User;
import com.blogapp.blogappiapi.payloads.UserDto;
import com.blogapp.blogappiapi.repositories.UserRepo;
import com.blogapp.blogappiapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto user, Integer userId) {
        return null;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(Integer userId) {

    }

    private User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setAbout(user.getAbout());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}