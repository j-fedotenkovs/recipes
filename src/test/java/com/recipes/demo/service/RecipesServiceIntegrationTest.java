package com.recipes.demo.service;

import com.recipes.demo.dto.IngredientDto;
import com.recipes.demo.dto.RecipeDto;
import com.recipes.demo.mapper.IngredientMapper;
import com.recipes.demo.mapper.RecipeMapper;
import com.recipes.demo.model.Ingredient;
import com.recipes.demo.model.Recipe;
import com.recipes.demo.repository.IngredientRepository;
import com.recipes.demo.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecipesServiceIntegrationTest {

    @MockBean
    private IngredientRepository ingredientRepository;
    @MockBean
    private RecipeRepository recipeRepository;
    @SpyBean
    private IngredientMapper ingredientMapper;
    @SpyBean
    private RecipeMapper recipeMapper;
    @Autowired
    private RecipesService recipesService;

    @Test
    void getAllRecipes() {
        List<Recipe> recipes = getRecipes();
        when(recipeRepository.findAll()).thenReturn(recipes);

        List<RecipeDto> result = recipesService.getAllRecipes();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getIngredients().size());

        verify(recipeRepository, times(1)).findAll();
        verify(recipeMapper, times(1)).toDtoList(recipes);
        verify(recipeMapper, times(1)).toDto(recipes.get(0));
        verify(ingredientMapper, times(1)).toDtoList(recipes.get(0).getIngredients());
        verify(ingredientMapper, times(1)).toDto(recipes.get(0).getIngredients().get(0));
    }

    @Test
    void updateRecipe() {
        Recipe recipe = getRecipes().get(0);
        recipe.getIngredients().add(new Ingredient() {{
            setId(1);
        }});
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(ingredientRepository.findById(any())).thenReturn(Optional.of(recipe.getIngredients().get(1)));

        RecipeDto recipeDto = getRecipeDto();
        recipeDto.getIngredients().add(new IngredientDto() {{
            setId(1L);
        }});
        recipesService.updateRecipe(recipeDto);

        verify(recipeRepository, times(1)).findById(any());
        verify(recipeMapper, times(1)).updateEntity(recipe, recipeDto);
        verify(ingredientMapper, times(1)).toEntity(recipeDto.getIngredients().get(0), recipe);
        verify(ingredientRepository, times(1)).findById(1L);
        verify(ingredientMapper, times(1)).updateEntity(recipe.getIngredients().get(1),
                recipeDto.getIngredients().get(1));
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void deleteIngredients() {
        recipesService.deleteIngredients(Arrays.asList(1L, 2L));

        verify(ingredientRepository, times(2)).deleteById(any());
    }

    @Test
    void deleteRecipe() {
        recipesService.deleteRecipe(1);

        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void saveRecipe() {
        RecipeDto recipeDto = getRecipeDto();

        recipesService.saveRecipe(recipeDto);

        verify(recipeMapper, times(1)).toEntity(recipeDto);
        verify(ingredientMapper, times(1)).toEntityList(eq(recipeDto.getIngredients()), any());
        verify(ingredientMapper, times(1)).toEntity(eq(recipeDto.getIngredients().get(0)), any());
        verify(recipeRepository, times(1)).save(any());
    }

    private RecipeDto getRecipeDto() {
        return new RecipeDto() {{
            List<IngredientDto> ingredientDtoList = new ArrayList<>();
            ingredientDtoList.add(new IngredientDto());
            setIngredients(ingredientDtoList);
        }};
    }

    private List<Recipe> getRecipes() {
        Recipe recipe = new Recipe() {{
            List<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(new Ingredient());
            setIngredients(ingredients);
        }};
        return Collections.singletonList(recipe);
    }
}