package com.recipes.demo.mapper;

import com.recipes.demo.dto.RecipeDto;
import com.recipes.demo.model.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeMapperTest {

    @InjectMocks
    private RecipeMapper recipeMapper;
    @Mock
    private IngredientMapper ingredientMapper;


    @Test
    public void testToDtoList() {
        when(ingredientMapper.toDtoList(any())).thenReturn(new ArrayList<>());

        List<RecipeDto> expected = Arrays.asList(getDto());
        List<RecipeDto> result = recipeMapper.toDtoList(Arrays.asList(getRecipe()));

        assertEquals(1, result.size());
        assertThat(expected.get(0), samePropertyValuesAs(result.get(0)));
    }

    @Test
    public void testToEntity() {
        when(ingredientMapper.toEntityList(any(), any())).thenReturn(new ArrayList<>());

        Recipe result = recipeMapper.toEntity(getDto());
        assertThat(getRecipe(), samePropertyValuesAs(result, "id"));
    }

    private Recipe getRecipe() {
        return new Recipe() {{
            setId(1L);
            setName("Pizza");
            setImageUrl("url");
            setDescription("mmm");
            setIngredients(new ArrayList<>());
        }};
    }

    @Test
    public void testUpdateEntity() {
        Recipe recipe = new Recipe();

        recipeMapper.updateEntity(recipe, getDto());

        assertEquals("Pizza", getDto().getName());
        assertEquals("mmm", getDto().getDescription());
        assertEquals("url", getDto().getImagePath());
    }

    private RecipeDto getDto() {
        return new RecipeDto() {{
            setId(1L);
            setName("Pizza");
            setImagePath("url");
            setDescription("mmm");
            setIngredients(new ArrayList<>());
        }};
    }

}