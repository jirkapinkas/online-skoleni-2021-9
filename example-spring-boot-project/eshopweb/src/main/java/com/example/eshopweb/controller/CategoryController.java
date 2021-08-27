package com.example.eshopweb.controller;

import com.example.eshopweb.dto.CategoryDto;
import com.example.eshopweb.entity.Category;
import com.example.eshopweb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public List<CategoryDto> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<CategoryDto> category(@PathVariable int id) {
        return categoryService.findById(id);
    }

}
