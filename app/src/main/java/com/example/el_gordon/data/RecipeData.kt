package com.example.el_gordon.data

import android.content.Context
import com.example.el_gordon.models.Recipe
import com.example.el_gordon.models.Ingredient
import com.example.el_gordon.R

object RecipeData {
    private val recipeBase = listOf(
        Triple("recipe_butter_bread", 1, listOf(
            Ingredient(1, R.drawable.ing_bread),
            Ingredient(2, R.drawable.ing_butter),
            Ingredient(3, R.drawable.ing_jam),
            Ingredient(4, R.drawable.ing_cinnamon),
            Ingredient(5, R.drawable.ing_nuts),
            Ingredient(6, R.drawable.ing_honey)
        )),
        Triple("recipe_milkshake", 1, listOf(
            Ingredient(1, R.drawable.ing_milk),
            Ingredient(2, R.drawable.ing_strawberry),
            Ingredient(3, R.drawable.ing_sugar),
//            Ingredient(4, R.drawable.ing_vanilla),
            Ingredient(5, R.drawable.ing_ice),
            Ingredient(6, R.drawable.ing_cream)
        )),
        Triple("recipe_salad", 1, listOf(
            Ingredient(1, R.drawable.ing_lettuce),
//            Ingredient(2, R.drawable.ing_tomato),
            Ingredient(3, R.drawable.ing_cucumber),
            Ingredient(4, R.drawable.ing_olive_oil),
            Ingredient(5, R.drawable.ing_cheese),
            Ingredient(6, R.drawable.ing_olives)
        )),
        Triple("recipe_pasta", 1, listOf(
            Ingredient(1, R.drawable.ing_pasta),
//            Ingredient(2, R.drawable.ing_tomato_sauce),
            Ingredient(3, R.drawable.ing_meat),
            Ingredient(4, R.drawable.ing_onion),
            Ingredient(5, R.drawable.ing_garlic),
            Ingredient(6, R.drawable.ing_cheese)
        )),
        Triple("recipe_rice", 1, listOf(
            Ingredient(1, R.drawable.ing_rice),
            Ingredient(2, R.drawable.ing_water),
//            Ingredient(3, R.drawable.ing_carrot),
            Ingredient(4, R.drawable.ing_peas),
//            Ingredient(5, R.drawable.ing_chicken),
            Ingredient(4, R.drawable.ing_peas),
            Ingredient(6, R.drawable.ing_spices)
        )),
        Triple("recipe_omelette", 1, listOf(
//             Ingredient(1, R.drawable.ing_egg),
//             Ingredient(2, R.drawable.ing_salt),
             Ingredient(3, R.drawable.ing_cheese),
             Ingredient(4, R.drawable.ing_onion),
             Ingredient(5, R.drawable.ing_jamon),
//             Ingredient(6, R.drawable.ing_pepper)
        ))
    )

    fun getRecipes(context: Context): List<Recipe> {
        return recipeBase.map { (baseName, difficulty, ingredients) ->
            Recipe(
                id = getResByName("${baseName}${difficulty}", "id", context),
                imageRes = getResByName("${baseName}${difficulty}", "drawable", context),
                difficulty = difficulty,
                ingredients = ingredients
            )
        }
    }

    private fun getResByName(resName: String, resType: String, context: Context): Int {
        return context.resources.getIdentifier(resName, resType, context.packageName)
    }
}