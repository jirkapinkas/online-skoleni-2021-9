package com.example.eshopweb.controller;

import com.example.eshopweb.dto.ItemDto;
import com.example.eshopweb.exception.NotFoundException;
import com.example.eshopweb.pojo.Message;
import com.example.eshopweb.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/item")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    // http://localhost:8080/item
    // http://localhost:8080/item?orderBy=id&direction=ASC
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ItemDto> items(@RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "ASC") String direction) {
        return itemService.findAll(orderBy, direction);
    }

    // http://localhost:8080/item/1
    @GetMapping("/{id}")
    public /*Optional<ItemDto>*/ ItemDto item(@PathVariable int id) {
        return itemService.findById(id);
    }

    // http://localhost:8080/item
    @PostMapping
    public ItemDto insert(@Valid @RequestBody ItemDto itemDto) {
        itemDto.setId(0);
        return itemService.save(itemDto);
    }

    // http://localhost:8080/item/1
    @PutMapping("/{id}")
    public ItemDto update(@Valid @RequestBody ItemDto itemDto, @PathVariable int id) {
        itemDto.setId(id);
        return itemService.save(itemDto);
    }

    // http://localhost:8080/item/1
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        itemService.deleteById(id);
    }

}
