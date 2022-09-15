package com.erapps.foodrecipesapp.utils

import androidx.compose.ui.graphics.Color

fun colorizedTextByCategory(category: String): Color {
    return when(category) {
        "Beef" -> Color(0xFFFF5722)
        "Seafood" -> Color(0xFF00BCD4)
        "Breakfast" -> Color(0xFFFFC107)
        "Chicken" -> Color(0xFFF44336)
        "Dessert" -> Color(0xFFE91E63)
        "Goat" -> Color(0xFF9C27B0)
        "Lamb" -> Color(0xFFD19742)
        "Miscellaneous" -> Color(0xFF673AB7)
        "Pasta" -> Color(0xFFE1D24C)
        "Pork" -> Color(0xFFD675C6)
        "Side" -> Color(0xFF3F51B5)
        "Starter" -> Color(0xFF2196F3)
        "Vegan" -> Color(0xFF8BC34A)
        "Vegetarian" -> Color(0xFF338836)
        else -> Color(0xFF673AB7)
    }
}