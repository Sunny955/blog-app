package com.blogapp.blogappiapi.services.impl;

import com.blogapp.blogappiapi.entities.Category;
import com.blogapp.blogappiapi.entities.Post;
import com.blogapp.blogappiapi.entities.User;
import com.blogapp.blogappiapi.exceptions.ResourceNotFoundException;
import com.blogapp.blogappiapi.payloads.PostResponse;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private CategoryRepo categoryRepo;
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
    @CachePut(value = "posts",key = "#postId")
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts","Post Id",postId));
        CategoryDto categoryDto = postDto.getCategory();
        UserDto userDto = postDto.getUser();
        post.setContent(postDto.getContent());
        post.setCategory(this.modelMapper.map(categoryDto,Category.class));
        post.setUser(this.modelMapper.map(userDto,User.class));
        post.setTitle(postDto.getTitle());
        post.setAddedDate(new Date());
        if (postDto.getImageName() == null) {
            post.setImageName("default.png");
        } else {
            post.setImageName(postDto.getImageName());
        }

        Post savedPost = this.postRepo.save(post);
        return this.modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    @CacheEvict(value = "posts",allEntries = false,key = "#postId")
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts","Post Id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> posts = pagePost.getContent();

        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    @Cacheable(value="posts",key="#postId")
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts","Post Id",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    @Cacheable(value="posts",key="#categoryId")
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
        Category cat = this.modelMapper.map(categoryDto,Category.class);

        List<Post> posts = this.postRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    @Cacheable(value="posts",key="#userId")
    public List<PostDto> getPostsByUser(Integer userId) {
        UserDto userDto = this.userService.getUserById(userId);
        User user = this.modelMapper.map(userDto,User.class);

        List<Post> posts = this.postRepo.findByUser(user);
        return posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }
}