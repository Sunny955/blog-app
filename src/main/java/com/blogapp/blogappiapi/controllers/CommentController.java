package com.blogapp.blogappiapi.controllers;

import com.blogapp.blogappiapi.payloads.ApiResponse;
import com.blogapp.blogappiapi.payloads.dtos.CommentDto;
import com.blogapp.blogappiapi.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private CommentService commentService;
    @PostMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer userId, @PathVariable Integer postId){
        LOG.info("Called POST /api/comments/user/"+userId+"/post/"+postId);
        CommentDto createdComment = this.commentService.createComment(commentDto,userId,postId);
        return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> findComment(@RequestBody CommentDto commentDto,@PathVariable Integer commentId) throws Exception {
        LOG.info("Called PUT /api/comments/"+commentId);
        CommentDto updatedComment = this.commentService.editComment(commentDto, commentId);
        return new ResponseEntity<CommentDto>(updatedComment,HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Integer commentId) {
        LOG.info("Called GET /api/comments/"+commentId);
        CommentDto getComment = this.commentService.getComment(commentId);
        return new ResponseEntity<CommentDto>(getComment,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable Integer userId) {
        LOG.info("Called GET /api/comments/user/"+userId);
        List<CommentDto> commentDtos = this.commentService.getCommentByUser(userId);
        return new ResponseEntity<List<CommentDto>>(commentDtos,HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommnetsByPost(@PathVariable Integer postId) {
        LOG.info("Called GET /api/comments/post/"+postId);
        List<CommentDto> commentDtos = this.commentService.getCommentByPost(postId);
        return new ResponseEntity<List<CommentDto>>(commentDtos,HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        LOG.info("Called DELETE /api/comments/"+commentId);
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully with id:"+commentId,true),HttpStatus.OK);
    }
}