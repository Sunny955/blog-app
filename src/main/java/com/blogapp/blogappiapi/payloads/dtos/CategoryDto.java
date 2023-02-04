package com.blogapp.blogappiapi.payloads.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto implements Serializable {
    private Integer categoryId;
    @NotBlank
    @Size(min=4,max=50,message = "categoryTitle must be of min 5 characters and max of 50 characters!")
    private String categoryTitle;
    @NotBlank
    @Size(min=5,max=100,message = "categoryDescription must be of min 5 character and max of 100 characters!")
    private String categoryDescription;
}