package com.example.shopbackend.service;

import com.example.shopbackend.dto.CategoryDto;
import com.example.shopbackend.entity.Category;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.mapper.ProductRelatedMappers.CategoryMapper;
import com.example.shopbackend.repository.ProductRelatedRepositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAll() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false).map(categoryMapper::toDto).collect(Collectors.toList());
    }

    public CategoryDto getOneById(int id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category", id)));
    }

    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryRepository.findCategoryByName(categoryDto.getName());

        if(category != null)
            return categoryMapper.toDto(category);
        else
            return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
    }

    public CategoryDto update(CategoryDto categoryDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
    }

    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}
