package com.example.eshopweb.service;

import com.example.eshopweb.dto.CategoryDto;
import com.example.eshopweb.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<CategoryDto> findAll();
    Optional<CategoryDto> findById(int id);
}
