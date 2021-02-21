package com.recipes.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {

    private Long id;
    private String name;
    private String imagePath;
    private String description;
    private List<IngredientDto> ingredients;
}
