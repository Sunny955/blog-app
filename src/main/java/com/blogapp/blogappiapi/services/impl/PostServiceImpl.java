package com.blogapp.blogappiapi.services.impl;

import com.blogapp.blogappiapi.entities.Category;
import com.blogapp.blogappiapi.entities.Post;
import com.blogapp.blogappiapi.entities.User;
import com.blogapp.blogappiapi.payloads.dtos.CategoryDto;
import com.blogapp.blogappiapi.payloads.dtos.PostDto;
import com.blogapp.blogappiapi.payloads.dtos.UserDto;
import com.blogapp.blogappiapi.repositories.CategoryRepo;
import com.blogapp.blogappiapi.repositories.PostRepo;
import com.blogapp.blogappiapi.repositories.UserRepo;
import com.blogapp.blogappiapi.services.CategoryService;
import com.blogapp.blogappiapi.services.PostService;
import com.blogapp.blogappiapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {

        UserDto userDto = this.userService.getUserById(userId);
        User user = this.modelMapper.map(userDto,User.class);

        CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
        Category category = this.modelMapper.map(categoryDto,Category.class);

        Post createdPost = this.modelMapper.map(postDto,Post.class);
        createdPost.setImageName("default.png");
        createdPost.setAddedDate(new Date());
        createdPost.setUser(user);
        createdPost.setCategory(category);

        Post savedPost = this.postRepo.save(createdPost);
        return this.modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<PostDto> getAllPost() {
        return null;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        return null;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return null;
    }
}