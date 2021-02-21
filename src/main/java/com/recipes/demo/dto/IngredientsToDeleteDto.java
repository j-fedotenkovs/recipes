package com.recipes.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IngredientsToDeleteDto {
    public List<Long> ingredientIds = new ArrayList<>();
}
