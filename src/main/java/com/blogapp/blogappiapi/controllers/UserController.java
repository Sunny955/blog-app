package com.blogapp.blogappiapi.controllers;

import com.blogapp.blogappiapi.payloads.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.blogapp.blogappiapi.payloads.dtos.UserDto;
import com.blogapp.blogappiapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        LOG.info("Called POST /api/users/");
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
       LOG.info("Called PUT /api/users"+userId);
       UserDto updatedUserDto =  this.userService.updateUser(userDto,userId);
       return ResponseEntity.ok(updatedUserDto);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
        LOG.info("Called DELETE /api/users/"+userId);
        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully with id:"+userId,true),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        LOG.info("Called GET /api/users");
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
        LOG.info("Called GET /api/user/"+userId);
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}