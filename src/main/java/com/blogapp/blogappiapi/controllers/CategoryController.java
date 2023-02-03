package com.blogapp.blogappiapi.controllers;

import com.blogapp.blogappiapi.payloads.ApiResponse;
import com.blogapp.blogappiapi.payloads.dtos.CategoryDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.blogapp.blogappiapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        LOG.info("Called POST /api/categories/");
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId) {
        LOG.info("Called PUT /api/categories/"+categoryId);
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        LOG.info("Called DELETE /api/categories/"+categoryId);
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully with id:"+categoryId,true),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
        LOG.info("Called GET /api/categories/"+categoryId);
        CategoryDto getCategory = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<CategoryDto>(getCategory,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategory() {
        LOG.info("Called GET /api/categories/");
        List<CategoryDto> categoryDtos = this.categoryService.getCategories();
        return new ResponseEntity<List<CategoryDto>>(categoryDtos,HttpStatus.OK);
    }
}