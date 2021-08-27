package com.example.eshopweb.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ItemDto {

    private int id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    private String description;

    @Min(1)
    private double price;

    private CategoryDto category;

}
