package com.erapps.foodrecipesapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erapps.foodrecipesapp.ui.screens.bycategory.SearchRecipesByCategoryScreen
import com.erapps.foodrecipesapp.ui.screens.details.DetailsScreen
import com.erapps.foodrecipesapp.ui.screens.random.RandomRecipeScreen
import com.erapps.foodrecipesapp.ui.screens.search.SearchRecipesScreen

@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {

    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = NavItem.Search.route
    ) {
        composable(NavItem.Search) {
            SearchRecipesScreen() {
                navController.navigate(NavItem.Details.createDetailsRoute(it)) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(NavItem.Category) {
            SearchRecipesByCategoryScreen() {
                navController.navigate(NavItem.Details.createDetailsRoute(it)) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(NavItem.Random) {
            RandomRecipeScreen() {
                navController.navigate(NavItem.Details.createDetailsRoute(it)) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(NavItem.Details) {
            val mealId = it.arguments?.getString("mealId").toString()

            DetailsScreen(mealId = mealId) {
                navController.popBackStack()
            }
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = navItem.route, arguments = navItem.args) {
        content(it)
    }
}