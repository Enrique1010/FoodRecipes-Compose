package com.erapps.foodrecipesapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erapps.foodrecipesapp.ui.navigation.NavGraph
import com.erapps.foodrecipesapp.ui.theme.FoodRecipesAppTheme

@Composable
fun MainScreen() {
    FoodRecipesAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            NavGraph()
        }
    }
}