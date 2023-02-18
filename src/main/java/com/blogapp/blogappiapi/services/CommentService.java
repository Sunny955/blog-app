package com.blogapp.blogappiapi.services;

import com.blogapp.blogappiapi.entities.Comment;
import com.blogapp.blogappiapi.payloads.dtos.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer userId,Integer postId);
    void deleteComment(Integer commentId);
    CommentDto editComment(CommentDto commentDto,Integer commentId) throws Exception;
    CommentDto getComment(Integer commentId);
    List<CommentDto> getCommentByUser(Integer userId);
    List<CommentDto> getCommentByPost(Integer postId);
}