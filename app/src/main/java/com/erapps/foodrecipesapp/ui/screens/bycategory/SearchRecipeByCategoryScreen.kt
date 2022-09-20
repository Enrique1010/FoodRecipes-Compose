package com.erapps.foodrecipesapp.ui.screens.bycategory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.erapps.foodrecipesapp.data.models.ShortMeal
import com.erapps.foodrecipesapp.ui.shared.ErrorScreen
import com.erapps.foodrecipesapp.ui.shared.LoadingScreen
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.utils.getImageDominantColor

@Composable
fun SearchRecipesByCategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchByCategoryScreenViewModel = hiltViewModel(),
    onRecipeClick: (String) -> Unit
) {

    val uiState = viewModel.uiState.value

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        DropDownCategories {
            viewModel.getRecipesByCategory(it)
        }
        Spacer(modifier = modifier.height(8.dp))

        when (uiState) {
            is UiState.Error -> ErrorScreen(
                errorMessage = uiState.errorMessage,
                errorStringResource = uiState.errorStringResource
            )
            UiState.Loading -> LoadingScreen()
            is UiState.Success<*> -> {
                @Suppress("UNCHECKED_CAST")
                ListOfShortRecipes(
                    shortRecipes = uiState.data as List<ShortMeal>,
                    onRecipeClick = onRecipeClick
                )
            }
            else -> LoadingScreen()
        }
    }
}

//list view
@Composable
fun ListOfShortRecipes(
    shortRecipes: List<ShortMeal>,
    onRecipeClick: (String) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        items(shortRecipes) { shortMeal ->
            ListOfShortItem(shortMeal = shortMeal, onRecipeClick = onRecipeClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListOfShortItem(
    modifier: Modifier = Modifier,
    shortMeal: ShortMeal,
    onRecipeClick: (String) -> Unit
) {
    val context = LocalContext.current
    val defaultColor = MaterialTheme.colors.primary
    val backGroundColor = remember { mutableStateOf(defaultColor) }

    Card(
        onClick = { onRecipeClick(shortMeal.idMeal) },
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .padding(8.dp)
            .border(
                BorderStroke(2.dp, backGroundColor.value),
                shape = Shapes().small
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(shortMeal.strMealThumb)
                    .crossfade(true)
                    .build(),
                contentDescription = shortMeal.strMeal,
                alignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth(),
                loading = { LinearProgressIndicator() },
                onSuccess = { painter ->
                    getImageDominantColor(backGroundColor, painter.result.drawable)
                }
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier.padding(4.dp),
                    text = shortMeal.strMeal,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// dropdown view
@Composable
fun DropDownCategories(
    modifier: Modifier = Modifier,
    isDropDownOpened: Boolean = false,
    onDropDownItemClick: (String) -> Unit
) {

    val categories = getCategories()
    val isOpen = remember { mutableStateOf(isDropDownOpened) }
    val selectedItem = rememberSaveable { mutableStateOf(categories[0].value) }

    Spacer(modifier = modifier.height(8.dp))
    DropDownHeader(isOpen = isOpen, selectedItem = selectedItem)
    Spacer(modifier = modifier.height(8.dp))
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        DropDownBox(isOpen, categories, selectedItem, onDropDownItemClick)
    }
}

@Composable
private fun DropDownHeader(
    modifier: Modifier = Modifier,
    isOpen: MutableState<Boolean>,
    selectedItem: MutableState<String>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp)
            .padding(end = 16.dp, start = 16.dp)
            .border(
                width = 1.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primary.copy(1f)
                    )
                ),
                shape = CircleShape
            )
            .clickable { isOpen.value = !isOpen.value },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DropDownHeaderContent(selectedItem = selectedItem, isOpen = isOpen)
    }
}

@Composable
private fun DropDownHeaderContent(
    modifier: Modifier = Modifier,
    selectedItem: MutableState<String>,
    isOpen: MutableState<Boolean>
) {
    Text(
        modifier = modifier.padding(start = 8.dp),
        text = selectedItem.value,
        fontWeight = FontWeight.Bold
    )
    Icon(
        modifier = modifier
            .padding(end = 8.dp)
            .scale(1f, if (isOpen.value) 1f else -1f),
        imageVector = Icons.Default.ArrowDropUp,
        contentDescription = null
    )
}

@Composable
private fun DropDownBox(
    isOpen: MutableState<Boolean>,
    categories: List<Category>,
    selectedItem: MutableState<String>,
    onDropDownItemClick: (String) -> Unit
) {

    DropdownMenu(
        offset = DpOffset(16.dp, 0.dp),
        expanded = isOpen.value,
        properties = PopupProperties(dismissOnBackPress = true),
        onDismissRequest = { isOpen.value = !isOpen.value }
    ) {
        categories.forEach {
            DropdownMenuItem(
                onClick = {
                    selectedItem.value = it.value
                    onDropDownItemClick(it.value)
                    isOpen.value = false
                }
            ) {
                Text(text = it.value)
            }
        }
    }
}

