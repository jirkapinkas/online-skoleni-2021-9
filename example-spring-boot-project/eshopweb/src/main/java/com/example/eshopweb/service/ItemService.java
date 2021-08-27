package com.example.eshopweb.service;

import com.example.eshopweb.dto.ItemDto;
import com.example.eshopweb.entity.Item;
import com.example.eshopweb.exception.NotFoundException;
import com.example.eshopweb.mapper.ItemMapper;
import com.example.eshopweb.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Transactional
    public void exampleSaveManaged() {
        Item item = new Item(); // entita je ve stavu "New"
        // "Managed" = uvnitr aktivni transakce ziskame zaznam z databaze / ulozime zaznam do databaze => vysledny zaznam je ve stavu "Managed"
        Item managedItem = itemRepository.save(item); // entita je ve stavu "Managed"
        managedItem.setName("Stuff"); // Predtim nez se ukonci transakce, tak Hibernate vygeneruje UPDATE a nastavi stav entity
        // TADY NEMUSIME VOLAT itemRepository.save(managedItem) ABY SE PROVEDL UPDATE ENTITY!!!
    }

    public void exampleSaveDetached() {
        Item item = new Item(); // entita je ve stavu "New"
        // "Detached" = ziskame zaznam z databaze / ulozime zaznam do databaze, ALE NEJSME UVNITR AKTIVNI TRANSAKCE => vysledny zaznam je ve stavu "Detached"
        Item detachedItem = itemRepository.save(item); // entita je ve stavu "Detached"
        detachedItem.setName("Stuff"); // Zmenili jsme stav objektu, TATO ZMENA SE NEPROPISE DO DATABAZE!!!
        // POKUD CHCEME PROVEST UPDATE ENTITY, TAK TADY MUSIME ZAVOLAT itemRepository.save(detachedItem) ABY SE PROVEDL UPDATE ENTITY!!!
    }

    @Transactional
    public void exampleSaveAndFlush() {
        Item item = new Item(); // entita je ve stavu "New"
        // "Managed" = uvnitr aktivni transakce ziskame zaznam z databaze / ulozime zaznam do databaze => vysledny zaznam je ve stavu "Managed"
        // POZOR!!! Operace save() negarantuje, ze ihned provede INSERT / UPDATE!!! (obdobne metoda delete() negaranguje okamzite provedeni DELETE)
        // Operace INSERT / UPDATE / DELETE generovane Hibernaty se provadeji kdyz se provede FLUSH
        // Je garantovane, ze se FLUSH provede pred COMMITem (pred ukoncenim transakce)
        // Nebo kdyz to ma vyznam kvuli konzistenci dat
        // Nebo kdyz zavolate saveAndFlush(item)
        Item managedItem = itemRepository.save(item); // entita je ve stavu "Managed"
        managedItem.setName("Stuff"); // Predtim nez se ukonci transakce, tak Hibernate vygeneruje UPDATE a nastavi stav entity
        // TADY NEMUSIME VOLAT itemRepository.save(managedItem) ABY SE PROVEDL UPDATE ENTITY!!!
    }

    public void transactions5() {
        // Kazda tato metoda je implicitne transakcni!!!
        // => bude 5 transakci v databazi & to znamena, ze se 5x zavola COMMIT do databaze
        itemRepository.findById(1);
        itemRepository.findById(2);
        itemRepository.findById(3);
        itemRepository.findById(4);
        itemRepository.findById(5);
    }

    @Transactional(readOnly = true)
    public void transactions1() {
        // => bude 1 transakce v databazi
        itemRepository.findById(1);
        itemRepository.findById(2);
        itemRepository.findById(3);
        itemRepository.findById(4);
        itemRepository.findById(5);
    }

    @Cacheable("items")
//    @Transactional(readOnly = true)
    public List<ItemDto> findAll(String orderBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction.toUpperCase()), orderBy);
        List<Item> items = itemRepository.findAll(sort);
//        List<Item> items = itemRepository.findAllFetchCategory(sort);
        return itemMapper.toDto(items);
    }

    @Cacheable(cacheNames = "item", key = "#id")
//    @Transactional(readOnly = true)
    public /*Optional<ItemDto>*/ ItemDto findById(int id) {
        return itemRepository.findById(id)
//        return itemRepository.findByIdFetchCategory(id)
                .map(itemMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Item with id: '" + id + "' does not exist!"));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "items", allEntries = true),
            @CacheEvict(cacheNames = "item", key="#id")
    })
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "items", allEntries = true),
            @CacheEvict(cacheNames = "item", key="#itemDto.id")
    })
    public ItemDto save(ItemDto itemDto) {
        Item entity = itemMapper.toEntity(itemDto);
        Item managedEntity = itemRepository.save(entity);
        return itemMapper.toDto(managedEntity);
    }

//    @Scheduled(fixedDelay = 10_000)
//    @CacheEvict(cacheNames = {"items", "item"}, allEntries = true)
//    public void clearCache() {
//        log.info("cache cleared ...");
//    }

}
