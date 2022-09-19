package com.erapps.foodrecipesapp.utils

import com.erapps.foodrecipesapp.BuildConfig
import java.util.*

const val INGREDIENTS_IMAGE_PATH = BuildConfig.TheMealDB_Ingredients_Images_URL

fun getIngredientsImageURL(ingredient: String): String {
    return "$INGREDIENTS_IMAGE_PATH/${ingredient.uppercase(Locale.getDefault())}.png"
}