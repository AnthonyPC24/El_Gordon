package com.example.el_gordon.data

import com.example.el_gordon.models.Recipe
import com.example.el_gordon.models.Ingredient
import com.example.el_gordon.R

object RecipeData {
    val recipes = listOf(
        Recipe(
            id = R.id.recipe_butter_bread1,
            imageRes = R.drawable.recipe_butter_bread1,
            ingredients = listOf(
                Ingredient(1, R.drawable.ing_bread),
                Ingredient(2, R.drawable.ing_cheese)
            )
        ),
        Recipe(
            id = R.id.recipe_rice1,
            imageRes = R.drawable.recipe_rice1,
            ingredients = listOf(
                Ingredient(1, R.drawable.ing_milk),
                Ingredient(2, R.drawable.ing_cheese)
            )
        ),
        Recipe(
            id = R.id.recipe_rice1,
            imageRes = R.drawable.recipe_rice1,
            ingredients = listOf(
                Ingredient(1, R.drawable.ing_milk),
                Ingredient(2, R.drawable.ing_cheese)
            )
        ),
        Recipe(
            id = R.id.recipe_rice1,
            imageRes = R.drawable.recipe_rice1,
            ingredients = listOf(
                Ingredient(1, R.drawable.ing_milk),
                Ingredient(2, R.drawable.ing_cheese)
            )
        ),
        Recipe(
            id = R.id.recipe_rice1,
            imageRes = R.drawable.recipe_rice1,
            ingredients = listOf(
                Ingredient(1, R.drawable.ing_milk),
                Ingredient(2, R.drawable.ing_cheese)
            )
        ),
        Recipe(
            id = R.id.recipe_rice1,
            imageRes = R.drawable.recipe_rice1,
            ingredients = listOf(
                Ingredient(1, R.drawable.ing_milk),
                Ingredient(2, R.drawable.ing_cheese)
            )
        )
    )
}