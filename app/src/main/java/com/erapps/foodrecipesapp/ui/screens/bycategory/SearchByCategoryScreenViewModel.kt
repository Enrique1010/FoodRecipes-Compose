package com.erapps.foodrecipesapp.ui.screens.bycategory

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erapps.foodrecipesapp.data.source.CategoryRepository
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.ui.shared.mapResultToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchByCategoryScreenViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    private val _uiState = mutableStateOf<UiState?>(null)
    val uiState: State<UiState?> = _uiState

    init {
        getRecipesByCategory("Beef")
    }

    fun getRecipesByCategory(category: String) = viewModelScope.launch {

        categoryRepository.getRecipesByCategory(category).collect { result ->
            mapResultToUiState(result, _uiState) { response ->
                _uiState.value = UiState.Success(response.meals)
            }
        }
    }
}