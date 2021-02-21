package com.recipes.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Ingredient_ID")
    private long id;

    @Column(name = "Name")
    private String name;
    @Column(name = "Amount")
    private int amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Recipe_ID")
    private Recipe recipe;
}
