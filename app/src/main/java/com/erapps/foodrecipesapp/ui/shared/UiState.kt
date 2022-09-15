package com.erapps.foodrecipesapp.ui.shared

sealed class UiState {
    data class Success<out T>(val data: T) : UiState()
    data class Error(
        val errorMessage: String = "",
        val errorStringResource: Int? = null,
        val exception: Exception? = null
    ) : UiState()
    object Loading : UiState()
    object Empty : UiState()
}
