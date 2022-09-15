package com.erapps.foodrecipesapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.erapps.foodrecipesapp.ui.navigation.NavGraph
import com.erapps.foodrecipesapp.ui.navigation.NavItem
import com.erapps.foodrecipesapp.ui.theme.FoodRecipesAppTheme

@Composable
fun MainScreen() {
    FoodRecipesAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MainScreenContent()
        }
    }
}

@Composable
fun MainScreenContent() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == NavItem.Search.route ||
                currentRoute == NavItem.Category.route ||
                currentRoute == NavItem.Random.route
            ) {
                MainBottomAppBar(navController, currentRoute)
            }
        }
    ) {
        NavGraph(navController)
    }
}

@Composable
fun MainBottomAppBar(
    navController: NavHostController,
    currentRoute: String?
) {

    val bottomItems = listOf(
        BottomItem.Search,
        BottomItem.Category,
        BottomItem.Random
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        bottomItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.LightGray,
                onClick = {
                    navController.navigate(item.route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class BottomItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Search: BottomItem(NavItem.Search.route, "Search", Icons.Default.Search)
    object Category: BottomItem(NavItem.Category.route, "Category", Icons.Default.Category)
    object Random: BottomItem(NavItem.Random.route, "Random", Icons.Default.Abc)
}