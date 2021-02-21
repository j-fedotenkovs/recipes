package com.recipes.demo.repository;

import com.recipes.demo.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Override
    List<Recipe> findAll();
}
