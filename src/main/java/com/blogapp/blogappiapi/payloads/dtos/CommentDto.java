package com.blogapp.blogappiapi.payloads.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommentDto implements Serializable {
    private Integer id;
    @NotBlank
    @Size(min=1,message="content should be min of 1 character!")
    private String content;
    @NotNull
    private UserDto user;
}