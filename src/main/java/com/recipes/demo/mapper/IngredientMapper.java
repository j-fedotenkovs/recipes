package com.recipes.demo.mapper;

import com.recipes.demo.dto.IngredientDto;
import com.recipes.demo.model.Ingredient;
import com.recipes.demo.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientMapper {

    public IngredientDto toDto(Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();

        if (ingredient != null) {
            ingredientDto.setId(ingredient.getId());
            ingredientDto.setName(ingredient.getName());
            ingredientDto.setAmount(ingredient.getAmount());
        }
        return ingredientDto;
    }

    public List<IngredientDto> toDtoList(List<Ingredient> ingredientList) {
        return ingredientList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Ingredient toEntity(IngredientDto ingredientDto, Recipe recipe) {
        Ingredient ingredient = new Ingredient();

        if (ingredientDto != null) {
            ingredient.setRecipe(recipe);
            ingredient.setName(ingredientDto.getName());
            ingredient.setAmount(ingredientDto.getAmount());
        }
        return ingredient;
    }

    public List<Ingredient> toEntityList(List<IngredientDto> ingredientDtos, Recipe recipe) {
        return ingredientDtos.stream()
                .map(dto -> this.toEntity(dto, recipe))
                .collect(Collectors.toList());
    }

    public void updateEntity(Ingredient ingredient, IngredientDto ingredientDto) {
        if (ingredientDto != null) {
            ingredient.setName(ingredientDto.getName());
            ingredient.setAmount(ingredientDto.getAmount());
        }
    }
}
