package com.recipes.demo.service;

import com.recipes.demo.dto.IngredientDto;
import com.recipes.demo.dto.RecipeDto;
import com.recipes.demo.mapper.IngredientMapper;
import com.recipes.demo.mapper.RecipeMapper;
import com.recipes.demo.model.Ingredient;
import com.recipes.demo.model.Recipe;
import com.recipes.demo.repository.IngredientRepository;
import com.recipes.demo.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipesServiceTest {

    @InjectMocks
    private RecipesService recipesService;
    @Mock
    private RecipeMapper recipeMapper;
    @Mock
    private IngredientMapper ingredientMapper;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    public void testGetAllRecipes() {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeDtoList.add(new RecipeDto());
        when(recipeMapper.toDtoList(any())).thenReturn(recipeDtoList);

        List<RecipeDto> result = recipesService.getAllRecipes();

        assertEquals(recipeDtoList, result);
        Mockito.verify(recipeMapper, times(1)).toDtoList(any());
        Mockito.verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testSaveRecipe() {
        Recipe recipe = new Recipe();
        when(recipeMapper.toEntity(any())).thenReturn(recipe);

        recipesService.saveRecipe(new RecipeDto());

        Mockito.verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    public void testUpdateRecipe() {
        when(recipeRepository.findById(any())).thenReturn(Optional.of(new Recipe()));
        when(ingredientMapper.toEntity(any(), any())).thenReturn(new Ingredient());
        when(ingredientRepository.findById(any())).thenReturn(Optional.of(new Ingredient()));

        recipesService.updateRecipe(getRecipeDto());

        Mockito.verify(recipeRepository, times(1)).findById(any());
        Mockito.verify(recipeMapper, times(1)).updateEntity(any(), any());
        Mockito.verify(ingredientMapper, times(1)).toEntity(any(), any());
        Mockito.verify(ingredientRepository, times(1)).findById(any());
        Mockito.verify(ingredientMapper, times(1)).updateEntity(any(), any());
        Mockito.verify(recipeRepository, times(1)).save(any());
    }

    @Test(expected = HttpClientErrorException.class)
    public void testUpdateRecipeException1() {
        when(recipeRepository.findById(any())).thenReturn(Optional.empty());
        recipesService.updateRecipe(new RecipeDto());
    }

    @Test(expected = HttpClientErrorException.class)
    public void testUpdateRecipeException2() {
        when(recipeRepository.findById(any())).thenReturn(Optional.of(new Recipe()));
        when(ingredientRepository.findById(any())).thenReturn(Optional.empty());

        recipesService.updateRecipe(getRecipeDto());
    }

    @Test
    public void testDeleteIngredients() {
        recipesService.deleteIngredients(Arrays.asList(1L, 2L));
        Mockito.verify(ingredientRepository, times(2)).deleteById(any());
    }

    @Test
    public void testDeleteRecipe() {
        recipesService.deleteRecipe(1);
        Mockito.verify(recipeRepository, times(1)).deleteById(1L);
    }

    private RecipeDto getRecipeDto() {
        RecipeDto recipeDto = new RecipeDto();
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        IngredientDto dto0 = new IngredientDto() {{
            setId(1L);
        }};
        IngredientDto dto1 = new IngredientDto();
        ingredientDtoList.add(dto0);
        ingredientDtoList.add(dto1);
        recipeDto.setIngredients(ingredientDtoList);
        return recipeDto;
    }
}