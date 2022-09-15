package com.erapps.foodrecipesapp.ui.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.erapps.foodrecipesapp.data.models.Meal
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.utils.colorizedTextByCategory

@Composable
fun SearchRecipesScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onCardClick: (String) -> Unit
) {
    viewModel.searchRecipesByName("")
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState is UiState.Loading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        //searchbar
        //SearchAppBar()
        //view
        when (uiState) {
            UiState.Empty -> {
                EmptyScreen()
            }
            is UiState.Error -> {
                ErrorScreen(uiState.errorMessage, uiState.errorStringResource)
            }
            is UiState.Success<*> -> {
                @Suppress("UNCHECKED_CAST")
                ListOfRecipes(
                    list = uiState.data as List<Meal>,
                    onCardClick = { onCardClick(it) }
                )
            }
            else -> {}
        }
    }
}

@Composable
fun SearchAppBar() {
    TODO("Not yet implemented")
}

@Composable
fun ErrorScreen(errorMessage: String, errorStringResource: Int?) {
    TODO("Not yet implemented")
}

@Composable
fun EmptyScreen() {
    TODO("Not yet implemented")
}

@Composable
fun ListOfRecipes(list: List<Meal>, onCardClick: (String) -> Unit) {

    LazyColumn {
        items(list) { meal ->
            ListOfRecipesItem(meal = meal) { onCardClick(meal.idMeal) }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListOfRecipesItem(
    modifier: Modifier = Modifier,
    meal: Meal,
    onCardClick: () -> Unit
) {
    val context = LocalContext.current
    val cardMainColor = colorizedTextByCategory(meal.strCategory)

    Card(
        modifier = modifier
            .padding(bottom = 8.dp)
            .wrapContentSize(),
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        border = BorderStroke(
            1.dp, brush = Brush.sweepGradient(
                colors = listOf(
                    cardMainColor,
                    cardMainColor.copy(
                        alpha = 1f,
                    )
                ),
                center = Offset.Unspecified
            )
        ),
        onClick = onCardClick
    ) {
        Row {
            AsyncImage(
                modifier = modifier
                    .size(124.dp, 124.dp),
                model = ImageRequest.Builder(context)
                    .data(meal.strMealThumb)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                alignment = Alignment.TopCenter
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = meal.strMeal,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = modifier.fillMaxWidth(),
                    color = cardMainColor,
                    fontWeight = FontWeight.Bold,
                    text = meal.strCategory
                )
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = meal.strArea
                )
            }
        }
    }
}