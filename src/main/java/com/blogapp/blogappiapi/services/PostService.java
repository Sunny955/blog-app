package com.blogapp.blogappiapi.services;

import com.blogapp.blogappiapi.entities.Post;
import com.blogapp.blogappiapi.payloads.PostResponse;
import com.blogapp.blogappiapi.payloads.dtos.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);
}