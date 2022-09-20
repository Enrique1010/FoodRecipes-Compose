package com.erapps.foodrecipesapp.ui.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.erapps.foodrecipesapp.R
import com.erapps.foodrecipesapp.data.models.Meal
import com.erapps.foodrecipesapp.ui.shared.ErrorScreen
import com.erapps.foodrecipesapp.ui.shared.ScreenWithMessage
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.utils.getImageDominantColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchRecipesScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onCardClick: (String) -> Unit
) {

    val uiState = viewModel.uiState.value
    val text = remember { mutableStateOf("") }
    val focused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchBar(
            query = text,
            onSearchClick = { viewModel.searchRecipesByName(text.value) },
            onBack = { text.value = "" },
            focused = focused
        )
        if (uiState is UiState.Loading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        when (uiState) {
            UiState.Empty -> {
                ScreenWithMessage(message = R.string.empty_text)
            }
            is UiState.Error -> {
                ErrorScreen(
                    errorMessage = uiState.errorMessage,
                    errorStringResource = uiState.errorStringResource
                )
            }
            is UiState.Success<*> -> {
                @Suppress("UNCHECKED_CAST")
                ListOfRecipes(
                    list = uiState.data as List<Meal>,
                    onCardClick = { onCardClick(it) }
                )
            }
            else -> {
                ScreenWithMessage(message = R.string.welcome_message)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: MutableState<String>,
    onSearchClick: () -> Unit,
    onBack: () -> Unit,
    focused: MutableState<Boolean>
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused.value) {
            // Back button
            IconButton(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onBack()
                }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }

        CustomTextField(
            focused = focused,
            value = query,
            onSearchClick = {
                onSearchClick()
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    focused: MutableState<Boolean>,
    value: MutableState<String>,
    onSearchClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focused.value = it.isFocused }
            .focusRequester(focusRequester),
        value = value.value,
        onValueChange = { value.value = it },
        placeholder = { Text(text = "Search...", color = MaterialTheme.colors.onBackground) },
        singleLine = true,
        trailingIcon = {
            if (focused.value) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = null
                    )
                }
            }
        },
        shape = CircleShape,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions { onSearchClick() },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            disabledTextColor = Color.Transparent,
            backgroundColor = MaterialTheme.colors.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
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
    val defaultColor = MaterialTheme.colors.primary
    val cardMainColor = remember { mutableStateOf(defaultColor) }

    Card(
        modifier = modifier
            .padding(bottom = 8.dp)
            .wrapContentSize(),
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        border = BorderStroke(
            1.dp,
            brush = Brush.verticalGradient(
                colors = listOf(
                    cardMainColor.value,
                    cardMainColor.value.copy(alpha = 1f)
                )
            )
        ),
        onClick = onCardClick
    ) {
        Row {
            ImageSection(imageUrl = meal.strMealThumb, cardMainColor = cardMainColor)
            ContentSection(meal = meal, cardMainColor = cardMainColor)
        }
    }
}

@Composable
private fun ContentSection(
    modifier: Modifier = Modifier,
    meal: Meal,
    cardMainColor: MutableState<Color>
) {
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
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = modifier.fillMaxWidth(),
            color = cardMainColor.value,
            fontWeight = FontWeight.Bold,
            text = meal.strCategory
        )
        Text(
            modifier = modifier.fillMaxWidth(),
            text = meal.strArea
        )
    }
}

@Composable
private fun ImageSection(
    modifier: Modifier = Modifier,
    imageUrl: String,
    cardMainColor: MutableState<Color>
) {
    val context = LocalContext.current

    SubcomposeAsyncImage(
        modifier = modifier.size(124.dp, 124.dp),
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        alignment = Alignment.TopCenter,
        loading = { LinearProgressIndicator() },
        onSuccess = { painter ->
            getImageDominantColor(cardMainColor, painter.result.drawable)
        }
    )
}