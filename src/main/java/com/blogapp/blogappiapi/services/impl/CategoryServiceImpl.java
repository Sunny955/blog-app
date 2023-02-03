package com.blogapp.blogappiapi.services.impl;

import com.blogapp.blogappiapi.entities.Category;
import com.blogapp.blogappiapi.exceptions.ResourceNotFoundException;
import com.blogapp.blogappiapi.payloads.CategoryDto;
import com.blogapp.blogappiapi.repositories.CategoryRepo;
import com.blogapp.blogappiapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto,Category.class);
        Category savedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(savedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());

        Category savedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(savedCat,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID",categoryId));
        this.categoryRepo.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID",categoryId));
        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();

        return categories.stream().map((category)-> this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
    }
}