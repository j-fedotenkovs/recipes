package com.recipes.demo.mapper;

import com.recipes.demo.dto.IngredientDto;
import com.recipes.demo.model.Ingredient;
import com.recipes.demo.model.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class IngredientMapperTest {

    private IngredientMapper ingredientMapper;

    @Before
    public void setUp() {
        ingredientMapper = new IngredientMapper();
    }

    @Test
    public void testToDtoList() {
        List<IngredientDto> expected = getIngredientDtoList();
        List<IngredientDto> result = ingredientMapper.toDtoList(getIngredientList());

        assertEquals(1, result.size());
    }

    @Test
    public void testToEntityList() {
        List<Ingredient> expected = getIngredientList();
        Recipe recipe = new Recipe();
        expected.get(0).setRecipe(recipe);
        List<Ingredient> result = ingredientMapper.toEntityList(getIngredientDtoList(), recipe);

        assertEquals(1, result.size());
        assertThat(expected.get(0), samePropertyValuesAs(result.get(0), "id"));
    }

    @Test
    public void testUpdateEntity() {
        Ingredient ingredient = new Ingredient();
        IngredientDto ingredientDto = getIngredientDtoList().get(0);

        ingredientMapper.updateEntity(ingredient, ingredientDto);

        assertEquals("Tomato", ingredientDto.getName());
        assertEquals(5, ingredientDto.getAmount());
    }

    private List<Ingredient> getIngredientList() {
        Ingredient ingredient = new Ingredient() {{
            setName("Tomato");
            setAmount(5);
            setId(1L);
        }};
        return Arrays.asList(ingredient);
    }

    private List<IngredientDto> getIngredientDtoList() {
        IngredientDto dto = new IngredientDto() {{
            setName("Tomato");
            setAmount(5);
            setId(1L);
        }};
        return Arrays.asList(dto);
    }
}