package com.erapps.foodrecipesapp.ui.screens.bycategory

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun SearchRecipesByCategoryScreen(onRecipeClick: (String) -> Unit) {
    SearchRecipesByCategoryScreenContent(onRecipeClick = onRecipeClick)
}

@Composable
fun SearchRecipesByCategoryScreenContent(
    modifier: Modifier = Modifier,
    onRecipeClick: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        DropDownCategories() {

        }
        Spacer(modifier = modifier.height(16.dp))
        //list
    }
}

@Composable
fun DropDownCategories(
    modifier: Modifier = Modifier,
    isDropDownOpened: Boolean = false,
    onDropDownItemClick: (String) -> Unit
) {

    val categories = getCategories()
    val isOpen = remember { mutableStateOf(isDropDownOpened) }
    val selectedItem = remember { mutableStateOf(categories[0].value) }

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

