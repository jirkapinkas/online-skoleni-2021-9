package com.example.eshopweb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    private String name;

    // vychozi nastaveni: LAZY, coz znamena: Kdyz se z databaze ziska Category, tak v atributu "items" je proxy objekt.
    // Kdyz se na nem zavola .get(0), .size(), ..., tak se provede SELECT do databaze a informace o objektech typu Item
    // se ziskaji z databaze.
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Item> items;

}
