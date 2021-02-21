package com.recipes.demo.controller;

import com.recipes.demo.dto.IngredientsToDeleteDto;
import com.recipes.demo.dto.RecipeDto;
import com.recipes.demo.service.RecipesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class RecipesController {

    private final RecipesService recipesService;

    @GetMapping("/recipes")
    public List<RecipeDto> getAllRecipes() {
        return recipesService.getAllRecipes();
    }

    @CrossOrigin
    @PostMapping("/recipe/save")
    public void saveRecipe(@RequestBody RecipeDto recipeDto) {
        recipesService.saveRecipe(recipeDto);
    }

    @PutMapping("/recipe/update")
    public void updateRecipe(@RequestBody RecipeDto recipeDto) {
        recipesService.updateRecipe(recipeDto);
    }

    @DeleteMapping("/recipe/{id}/delete")
    public void deleteRecipe(@PathVariable long id) {
        recipesService.deleteRecipe(id);
    }

    @CrossOrigin
    @PostMapping("/ingredients/delete")
    public void deleteIngredients(@RequestBody IngredientsToDeleteDto ids) {
        recipesService.deleteIngredients(ids.getIngredientIds());
    }
}
