package com.example.eshopweb.service;

import com.example.eshopweb.dto.CategoryDto;
import com.example.eshopweb.entity.Category;
import com.example.eshopweb.mapper.CategoryMapper;
import com.example.eshopweb.restrepository.RestCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private RestCategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    @Override
    public List<CategoryDto> findAll() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    @Override
    public Optional<CategoryDto> findById(int id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto);
    }
}
