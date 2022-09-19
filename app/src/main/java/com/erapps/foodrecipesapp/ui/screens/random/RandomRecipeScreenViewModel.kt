package com.erapps.foodrecipesapp.ui.screens.random

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.source.RandomScreenRepository
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.ui.shared.mapErrors
import com.erapps.foodrecipesapp.ui.shared.mapResultToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomRecipeScreenViewModel @Inject constructor(
    private val randomScreenRepository: RandomScreenRepository
): ViewModel() {

    private val _uiState = mutableStateOf<UiState?>(null)
    val uiState: State<UiState?> = _uiState

    fun getRandomRecipe() = viewModelScope.launch {

        randomScreenRepository.getRandomRecipe().collect { result ->
            mapResultToUiState(result, _uiState) { response ->
                val meal = response.meals?.get(0)

                meal?.let {
                    _uiState.value = UiState.Success(it)
                }
            }
        }
    }

}