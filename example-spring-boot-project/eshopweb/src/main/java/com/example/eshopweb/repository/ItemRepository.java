package com.example.eshopweb.repository;

import com.example.eshopweb.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @EntityGraph(Item.GRAPH_CATEGORY)
    @Override
    List<Item> findAll(Sort sort);

    @EntityGraph(Item.GRAPH_CATEGORY)
    @Override
    Optional<Item> findById(Integer integer);

//    @Query("select i from Item i left join fetch i.category")
//    List<Item> findAllFetchCategory(Sort sort);
//
//    @Query("select i from Item i left join fetch i.category where i.id = ?1")
//    Optional<Item> findByIdFetchCategory(int id);

    // query generovane na zaklade nazvu metody:

    // select * from item where name = ?1
    List<Item> findByName(String name);

    // select * from item where name = ?1 order by name
    List<Item> findByName(String name, Sort sort);

    // select * from item where name = ?1 order by name limit 10
    List<Item> findByName(String name, Pageable pageable);

    // select * from item where price > ?1
    List<Item> findByPriceGreaterThan(double minPrice);

    // query na zaklade HQL (JPQL):
    @Query("select i from Item i where i.price > ?1")
    List<Item> customFindByPriceGreaterThan(double minPrice);

    // query na zaklade SQL:
    @Query(nativeQuery = true, value = "select count(*) from item")
    long itemCount();

    // custom insert / update / delete operace:
    @Transactional
    @Modifying
    @Query("update Item i set i.price = i.price * 1.1")
    void increaseItemPriceBy10Percent();

}
