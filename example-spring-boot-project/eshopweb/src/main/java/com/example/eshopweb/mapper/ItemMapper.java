package com.example.eshopweb.mapper;

import com.example.eshopweb.dto.ItemDto;
import com.example.eshopweb.entity.Item;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item toEntity(ItemDto dto);
    List<Item> toEntity(List<ItemDto> dto);

    ItemDto toDto(Item item);
    List<ItemDto> toDto(List<Item> item);

    @AfterMapping
    default void afterMappingToDto(Item item, @MappingTarget ItemDto itemDto) {
        if(itemDto.getName() != null) {
            itemDto.setName(itemDto.getName().toUpperCase());
        }
    }

}
