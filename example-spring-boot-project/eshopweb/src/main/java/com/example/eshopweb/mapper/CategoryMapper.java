package com.example.eshopweb.mapper;

import com.example.eshopweb.dto.CategoryDto;
import com.example.eshopweb.dto.ItemDto;
import com.example.eshopweb.entity.Category;
import com.example.eshopweb.entity.Item;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDto dto);
    List<Category> toEntity(List<CategoryDto> dto);

    CategoryDto toDto(Category category);
    List<CategoryDto> toDto(List<Category> categories);

}
