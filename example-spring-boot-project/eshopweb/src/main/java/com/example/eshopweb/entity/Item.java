package com.example.eshopweb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@NamedEntityGraph(
        name = Item.GRAPH_CATEGORY,
        attributeNodes = {
                @NamedAttributeNode(value = "category")
        }, subgraphs = {
})
@Getter
@Setter
@Entity
public class Item {

    public static final String GRAPH_CATEGORY = "Graph.Item.Category";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;

    private String name;

    private String description;

    private double price;

    // vychozi nastaveni: EAGER, coz znamena: kdyz se ziska objekt typu Item z databaze, tak se take ziska jeho kategorie
//    @ManyToOne(fetch = FetchType.EAGER)
    // best practice:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
