package com.recipes.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Recipe_ID")
    private long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "ImageUrl")
    private String imageUrl;
    @Column(name = "Description")
    private String description;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER,
            mappedBy = "recipe")
    private List<Ingredient> ingredients = new ArrayList<>();
}
