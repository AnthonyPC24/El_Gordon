package com.example.el_gordon.models

data class Recipe(
    val id: Int,
    val imageRes: Int,
    val ingredients: List<Ingredient>,
    val difficulty: Int? = null   // opcional
                 ) {
    init {
        difficulty?.let { diff ->
            val maxIngredients = when(diff) {
                1 -> 2
                2 -> 4
                3 -> 6
                else -> 0
            }
            require(ingredients.size <= maxIngredients)
        }
    }
}
