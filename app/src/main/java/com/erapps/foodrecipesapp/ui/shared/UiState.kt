package com.erapps.foodrecipesapp.ui.shared

sealed class UiState {
    data class Success<out T>(val data: T) : UiState()
    data class Error(
        val errorMessage: String? = null,
        val errorStringResource: Int? = null
    ) : UiState()
    object Loading : UiState()
    object Empty : UiState()
}
