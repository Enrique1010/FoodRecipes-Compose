package com.erapps.foodrecipesapp.ui.screens.random

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.erapps.foodrecipesapp.data.models.Meal
import com.erapps.foodrecipesapp.ui.shared.ErrorScreen
import com.erapps.foodrecipesapp.ui.shared.LoadingScreen
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.utils.getImageDominantColor

@Composable
fun RandomRecipeScreen(onRecipeClick: (String) -> Unit) {

    //Todo check why the state is erased in this screen
    RandomRecipeScreenContent(onRecipeClick = onRecipeClick)
}

@Composable
fun RandomRecipeScreenContent(
    viewModel: RandomRecipeScreenViewModel = hiltViewModel(),
    onRecipeClick: (String) -> Unit
) {
    val uiState = viewModel.uiState.value
    val showRandomRecipe = remember { mutableStateOf(false) }

    when (uiState) {
        is UiState.Error -> {
            showRandomRecipe.value = false
            ErrorScreen(
                errorMessage = uiState.errorMessage,
                errorStringResource = uiState.errorStringResource
            )
        }
        UiState.Loading -> LoadingScreen()
        is UiState.Success<*> -> {
            RecipeItem(showRandomRecipe = showRandomRecipe,
                onRecipeClick = onRecipeClick,
                meal = uiState.data as Meal,
                onClick = {
                    viewModel.getRandomRecipe()
                    showRandomRecipe.value = true
                })
        }
        else -> RecipeItem(showRandomRecipe = showRandomRecipe,
            onRecipeClick = onRecipeClick,
            onClick = {
                viewModel.getRandomRecipe()
                showRandomRecipe.value = true
            })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    showRandomRecipe: MutableState<Boolean>,
    meal: Meal? = null,
    onRecipeClick: (String) -> Unit,
    onClick: () -> Unit
) {

    val defaultColor = MaterialTheme.colors.primary
    val backGroundColor = remember { mutableStateOf(defaultColor) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.sweepGradient(
                    colors = listOf(backGroundColor.value, MaterialTheme.colors.surface),
                    center = Offset.Infinite
                )
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showRandomRecipe.value) {
            Card(
                modifier = modifier.background(MaterialTheme.colors.surface),
                onClick = { meal?.idMeal?.let { onRecipeClick(it) } },
                shape = RoundedCornerShape(4.dp)
            ) {
                meal?.let {
                    Column(modifier = modifier.padding(8.dp)) {
                        ImageSection(imageUrl = it.strMealThumb, backGroundColor = backGroundColor)
                        TitleAndCategorySection(
                            title = it.strMeal,
                            category = it.strCategory,
                            backGroundColor = backGroundColor
                        )
                    }
                }
            }
        } else {
            Text(text = "Try Random Button.", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
        OutlinedButton(onClick = onClick) {
            Text(text = "Get Random Recipe", color = backGroundColor.value)
        }
    }
}

@Composable
private fun ImageSection(
    modifier: Modifier = Modifier,
    imageUrl: String,
    backGroundColor: MutableState<Color>
) {
    val context = LocalContext.current

    SubcomposeAsyncImage(model = ImageRequest.Builder(context).data(imageUrl).crossfade(true)
        .build(),
        contentDescription = null,
        loading = { LinearProgressIndicator() },
        alignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(.3f)
            .clip(RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop,
        onSuccess = { painter ->
            getImageDominantColor(backGroundColor, painter.result.drawable)
        }
    )
}

@Composable
private fun TitleAndCategorySection(
    modifier: Modifier = Modifier,
    title: String,
    category: String,
    backGroundColor: MutableState<Color>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = MaterialTheme.colors.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = category,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = backGroundColor.value,
        )
    }
}