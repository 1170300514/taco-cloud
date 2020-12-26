package com.xyzhang.tacocloud.data;

import com.xyzhang.tacocloud.objects.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
