package com.blogapp.blogappiapi.services.impl;

import com.blogapp.blogappiapi.entities.User;
import com.blogapp.blogappiapi.exceptions.ResourceNotFoundException;
import com.blogapp.blogappiapi.payloads.UserDto;
import com.blogapp.blogappiapi.repositories.UserRepo;
import com.blogapp.blogappiapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        LOG.info("Hitting db for creating a new user!");
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    @CachePut(value = "users",key = "#userId")
    public UserDto updateUser(UserDto userDto, Integer userId) {
        LOG.info("Hitting db for updating a user!");
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));

        user.setName(userDto.getName());
        user.setId(userDto.getId());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        User updatedUser = this.userRepo.save(user);

        return this.userToDto(updatedUser);
    }

    @Override
    @Cacheable(value = "users",key = "#userId",unless = "#result==null")
    public UserDto getUserById(Integer userId) {
        LOG.info("Hitting db for getting a user!");
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User"," Id ",userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        LOG.info("Hitting db for getting all users!");
        List<User> users = this.userRepo.findAll();

        List<UserDto> userDtos = users.stream().map(this::userToDto).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    @CacheEvict(value = "users",allEntries = false,key = "#userId")
    public void deleteUser(Integer userId) {
        LOG.info("Hitting db for deleting a user!");
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User"," Id ",userId));
        this.userRepo.deleteById(userId);
    }

    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto,User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setAbout(userDto.getAbout());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }
}