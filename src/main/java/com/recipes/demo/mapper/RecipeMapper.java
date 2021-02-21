package com.recipes.demo.mapper;

import com.recipes.demo.dto.RecipeDto;
import com.recipes.demo.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipeMapper {

    private final IngredientMapper ingredientMapper;

    public RecipeDto toDto(Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();

        if (recipe != null) {
            recipeDto.setId(recipe.getId());
            recipeDto.setName(recipe.getName());
            recipeDto.setImagePath(recipe.getImageUrl());
            recipeDto.setDescription(recipe.getDescription());
            recipeDto.setIngredients(ingredientMapper.toDtoList(recipe.getIngredients()));
        }
        return recipeDto;
    }

    public Recipe toEntity(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();

        if (recipeDto != null) {
            recipe.setName(recipeDto.getName());
            recipe.setDescription(recipeDto.getDescription());
            recipe.setImageUrl(recipeDto.getImagePath());
            recipe.setIngredients(ingredientMapper.toEntityList(recipeDto.getIngredients(), recipe));
        }
        return recipe;
    }

    public List<RecipeDto> toDtoList(List<Recipe> recipeList) {
        return recipeList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateEntity(Recipe recipe, RecipeDto recipeDto) {
        if (recipeDto != null) {
            recipe.setName(recipeDto.getName());
            recipe.setDescription(recipeDto.getDescription());
            recipe.setImageUrl(recipeDto.getImagePath());
        }
    }
}
