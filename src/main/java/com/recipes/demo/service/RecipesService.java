package com.recipes.demo.service;

import com.recipes.demo.dto.IngredientDto;
import com.recipes.demo.dto.RecipeDto;
import com.recipes.demo.mapper.IngredientMapper;
import com.recipes.demo.mapper.RecipeMapper;
import com.recipes.demo.model.Ingredient;
import com.recipes.demo.model.Recipe;
import com.recipes.demo.repository.IngredientRepository;
import com.recipes.demo.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipesService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientMapper ingredientMapper;
    private final RecipeMapper recipeMapper;

    public List<RecipeDto> getAllRecipes() {
        return recipeMapper.toDtoList(recipeRepository.findAll());
    }

    public void saveRecipe(RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.toEntity(recipeDto);
        recipeRepository.save(recipe);
    }

    public void updateRecipe(RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.findById(recipeDto.getId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        recipeMapper.updateEntity(recipe, recipeDto);

        List<IngredientDto> ingredientDtos = recipeDto.getIngredients();
        if (!ingredientDtos.isEmpty()) {
            List<Ingredient> ingredients = ingredientDtos.stream()
                    .map(dto -> dto.getId() == null ? ingredientMapper.toEntity(dto, recipe) : updateIngredient(dto))
                    .collect(Collectors.toList());
            recipe.setIngredients(ingredients);
        }
        recipeRepository.save(recipe);
    }

    public void deleteIngredients(List<Long> ids) {
        if (!ids.isEmpty()) {
            ids.forEach(ingredientRepository::deleteById);
        }
    }

    public void deleteRecipe(long id) {
        recipeRepository.deleteById(id);
    }

    private Ingredient updateIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientRepository.findById(ingredientDto.getId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ingredientMapper.updateEntity(ingredient, ingredientDto);
        return ingredient;
    }
}
