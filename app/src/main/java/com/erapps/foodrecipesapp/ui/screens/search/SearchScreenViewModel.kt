package com.erapps.foodrecipesapp.ui.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erapps.foodrecipesapp.data.source.SearchDefaultRepository
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.ui.shared.mapResultToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchDefaultRepository: SearchDefaultRepository
) : ViewModel() {

    private var _uiState = mutableStateOf<UiState?>(null)
    val uiState: State<UiState?> = _uiState

    fun searchRecipesByName(name: String) = viewModelScope.launch {

        searchDefaultRepository.searchRecipesByName(name).collect { result ->
            mapResultToUiState(result, _uiState) { response ->
                val meals = response.meals

                if (meals?.size == 0 || meals == null) {
                    _uiState.value = UiState.Empty
                    return@mapResultToUiState
                }

                _uiState.value = UiState.Success(meals)
            }
        }
    }

}