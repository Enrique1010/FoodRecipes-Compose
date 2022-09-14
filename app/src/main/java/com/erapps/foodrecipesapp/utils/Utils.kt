package com.erapps.foodrecipesapp.utils

import java.util.*

const val INGREDIENTS_IMAGE_PATH = "www.themealdb.com/images/ingredients"

fun getIngredientsImageURL(ingredient: String): String {
    return "$INGREDIENTS_IMAGE_PATH/${ingredient.uppercase(Locale.getDefault())}.jpg"
}