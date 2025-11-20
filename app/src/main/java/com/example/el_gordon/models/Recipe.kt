package com.example.el_gordon.models

data class Recipe(
    val id: Int,
    val imageRes: Int,
    val ingredients: List<Ingredient>,
    val difficulty: Int
)