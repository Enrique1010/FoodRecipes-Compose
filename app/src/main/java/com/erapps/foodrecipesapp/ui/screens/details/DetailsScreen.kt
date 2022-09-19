package com.erapps.foodrecipesapp.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.erapps.foodrecipesapp.data.models.Meal
import com.erapps.foodrecipesapp.ui.shared.ErrorScreen
import com.erapps.foodrecipesapp.ui.shared.LoadingScreen
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.utils.getImageDominantColor
import com.erapps.foodrecipesapp.utils.getIngredientsImageURL

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    mealId: String,
    onBackPressed: () -> Unit
) {

    LaunchedEffect(mealId) {
        viewModel.getRecipeById(mealId)
    }
    val uiState = viewModel.uiState.value

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (uiState) {
            UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Error -> {
                ErrorScreen(
                    errorMessage = uiState.errorMessage,
                    errorStringResource = uiState.errorStringResource
                )
            }
            is UiState.Success<*> -> {
                DetailsScreenContent(
                    meal = uiState.data as Meal,
                    ingredients = viewModel.ingredientsList.value.toList(),
                    ingredientsName = viewModel.ingredientsNames,
                    backGroundColor = viewModel.backGroundColorByImage
                )
            }
            else -> {}
        }
        BackButtonBar(modifier, onBackPressed)
    }

}

@Composable
private fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    meal: Meal,
    ingredients: List<String?>,
    ingredientsName: List<String?>,
    backGroundColor: MutableState<Color>
) {

    Box(
        modifier = modifier.verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.background(
                Brush.verticalGradient(
                    listOf(
                        backGroundColor.value,
                        MaterialTheme.colors.surface,
                        MaterialTheme.colors.surface
                    )
                )
            ),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageSection(imageUrl = meal.strMealThumb)
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                TitleAndCategorySection(
                    title = meal.strMeal,
                    category = meal.strCategory,
                    backGroundColor = backGroundColor
                )
                InstructionsSection(instructions = meal.strInstructions)
                ListOfIngredients(
                    ingredients = ingredients,
                    ingredientsNames = ingredientsName
                )
            }
        }
    }
}

@Composable
private fun BackButtonBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.Transparent),
        contentAlignment = Alignment.TopStart
    ) {
        Icon(
            modifier = modifier
                .size(32.dp)
                .offset(8.dp, 8.dp)
                .clickable { onBackPressed() },
            imageVector = Icons.Default.ArrowBack,
            tint = Color.White,
            contentDescription = null
        )
    }
}

@Composable
private fun ImageSection(
    modifier: Modifier = Modifier,
    imageUrl: String,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        loading = { LinearProgressIndicator() },
        alignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxWidth(.7f)
            .clip(RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop,
        onSuccess = { painter ->
            getImageDominantColor(viewModel.backGroundColorByImage, painter.result.drawable)
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
        modifier = modifier.fillMaxWidth()
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

@Composable
private fun InstructionsSection(
    modifier: Modifier = Modifier,
    instructions: String
) {

    val dynamicMaxLines = remember { mutableStateOf(15) }

    Spacer(modifier = modifier.height(16.dp))
    Divider()
    Text(
        modifier = modifier.clickable {
            dynamicMaxLines.value =
                if (dynamicMaxLines.value == Int.MAX_VALUE) 15 else Int.MAX_VALUE
        },
        text = instructions,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        maxLines = dynamicMaxLines.value,
        overflow = TextOverflow.Ellipsis
    )
    Divider()
    Spacer(modifier = modifier.height(16.dp))
}

@Composable
private fun ListOfIngredients(
    modifier: Modifier = Modifier,
    ingredients: List<String?>,
    ingredientsNames: List<String?>
) {

    val context = LocalContext.current

    Spacer(modifier = modifier.height(4.dp))
    Text(
        text = "Ingredients: ",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = modifier.height(4.dp))

    //set ingredients list and images
    ingredients.forEachIndexed { index, ingredient ->
        ingredient?.let {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = modifier
                        .size(48.dp, 48.dp)
                        .clip(CircleShape)
                        .padding(8.dp),
                    model = ImageRequest.Builder(context)
                        .data(getIngredientsImageURL(ingredientsNames[index]!!))
                        .crossfade(true)
                        .build(),
                    contentDescription = null
                )
                Spacer(modifier = modifier.width(8.dp))
                Text(text = it, fontWeight = FontWeight.Bold)
            }
        }
    }
}