package com.erapps.foodrecipesapp.ui.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.source.SearchDefaultRepository
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.ui.shared.mapErrors
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
            when (result) {
                is Result.Loading -> _uiState.value = UiState.Loading
                is Result.Error -> mapErrors(result, _uiState)
                is Result.Success -> {
                    val meals = result.data?.meals

                    if (meals?.size == 0 || result.data?.meals == null) {
                        _uiState.value = UiState.Empty
                        return@collect
                    }
                    //success code
                    meals?.let {
                        _uiState.value = UiState.Success(it)
                        return@collect
                    }
                }
            }
        }
    }

}