package com.blogapp.blogappiapi.payloads.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto implements Serializable {
    private Integer postId;
    @NotBlank
    @Size(min=3, max=50,message = "title should be min of 3 characters and max of 50 characters!")
    private String title;
    @Size(max = 100000,message = "content can be max of 100000 characters!")
    private String content;
    private String imageName;
    private Date addedDate;
    @NotNull
    private CategoryDto category;
    @NotNull
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();
}