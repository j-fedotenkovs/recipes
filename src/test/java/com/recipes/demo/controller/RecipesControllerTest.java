package com.recipes.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.demo.dto.IngredientsToDeleteDto;
import com.recipes.demo.dto.RecipeDto;
import com.recipes.demo.repository.RecipeRepository;
import com.recipes.demo.service.RecipesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(RecipesController.class)
public class RecipesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipesService recipesService;
    @MockBean
    private RecipeRepository recipeRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllRecipes() throws Exception {
        when(recipesService.getAllRecipes()).thenReturn(new ArrayList<>());

        String result = mockMvc.perform(
                get("/recipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(objectMapper.writeValueAsString(new ArrayList<>())).isEqualTo(result);
        Mockito.verify(recipesService, times(1)).getAllRecipes();
    }

    @Test
    public void testSaveRecipe() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/recipe/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RecipeDto())))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(recipesService, times(1)).saveRecipe(any());
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        MvcResult result = mockMvc.perform(
                put("/recipe/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RecipeDto())))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(recipesService, times(1)).updateRecipe(any());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        MvcResult result = mockMvc.perform(
                delete("/recipe/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(recipesService, times(1)).deleteRecipe(1);
    }

    @Test
    public void testDeleteIngredients() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/ingredients/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new IngredientsToDeleteDto())))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(recipesService, times(1)).deleteIngredients(any());
    }
}