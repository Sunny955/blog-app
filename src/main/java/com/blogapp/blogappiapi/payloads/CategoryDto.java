package com.blogapp.blogappiapi.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    @NotBlank
    @Size(min=4,max=50,message = "categoryTitle must be of min 5 characters and max of 50 characters!")
    private String categoryTitle;
    @NotBlank
    @Size(min=5,max=100,message = "categoryDescription must be of min 5 character and max of 100 characters!")
    private String categoryDescription;
}