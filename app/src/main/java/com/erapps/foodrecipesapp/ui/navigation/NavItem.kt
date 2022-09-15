package com.erapps.foodrecipesapp.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    private val navArgs: List<NavArgs> = emptyList()
) {

    val route = run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute).plus(argKeys).joinToString("/")
    }

    val args = navArgs.map { navArgument(name = it.key) { type = it.argType } }

    //bottom navigation
    object Search: NavItem("search")
    object Category: NavItem("category")
    object Random: NavItem("random")
    //details navigation
    object Details: NavItem("details", listOf(NavArgs.MealId)) {
        fun createDetailsRoute(mealId: String)= "$baseRoute/$mealId"
    }
}

enum class NavArgs(val key: String, val argType: NavType<*>) {
    MealId("mealId", NavType.StringType)
}
