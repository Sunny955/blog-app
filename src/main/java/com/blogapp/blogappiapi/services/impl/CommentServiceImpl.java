package com.blogapp.blogappiapi.services.impl;

import com.blogapp.blogappiapi.entities.Comment;
import com.blogapp.blogappiapi.entities.Post;
import com.blogapp.blogappiapi.entities.User;
import com.blogapp.blogappiapi.exceptions.ResourceNotFoundException;
import com.blogapp.blogappiapi.payloads.dtos.CommentDto;
import com.blogapp.blogappiapi.repositories.CommentRepo;
import com.blogapp.blogappiapi.repositories.PostRepo;
import com.blogapp.blogappiapi.repositories.UserRepo;
import com.blogapp.blogappiapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","User Id",userId));
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(commentDto.getContent());
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","Comment Id",commentId));
        this.commentRepo.deleteById(commentId);
    }
    @Override
    public CommentDto getComment(Integer commentId) {
        Comment getComment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","Comment Id",commentId));
        return this.modelMapper.map(getComment,CommentDto.class);
    }

    @Override
    @Cacheable(value="comments",key="#userId")
    public List<CommentDto> getCommentByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","User Id",userId));
        List<Comment> getComments = this.commentRepo.findByUser(user);
        return getComments.stream().map((comment)-> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }
    @Override
    @Cacheable(value = "comments",key="#postId")
    public List<CommentDto> getCommentByPost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
        List<Comment> comments = this.commentRepo.findByPost(post);
        return comments.stream().map((comment)-> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }
    @Override
    public CommentDto editComment(CommentDto commentDto, Integer commentId) throws Exception {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","Comment Id", commentId.intValue()));
        comment.setContent(commentDto.getContent());
        Comment updatedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(updatedComment,CommentDto.class);
    }
}