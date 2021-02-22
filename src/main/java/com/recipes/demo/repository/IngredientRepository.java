package com.recipes.demo.repository;

import com.recipes.demo.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    @Override
    List<Ingredient> findAll();
}
