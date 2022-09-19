package com.erapps.foodrecipesapp.ui.shared

import androidx.compose.runtime.MutableState
import com.erapps.foodrecipesapp.R
import com.erapps.foodrecipesapp.data.Result
import java.net.SocketTimeoutException

fun mapErrors(result: Result.Error<*>, uiState: MutableState<UiState?>) {
    when {
        result.isApiError -> {
            if (result.code in 500..599) {
                uiState.value = UiState.Error(errorStringResource = R.string.error_server)
                return
            }
            uiState.value = UiState.Error(errorMessage = result.exception?.message!!)
        }
        result.isNetworkError -> {
            if (result.exception is SocketTimeoutException) {
                uiState.value = UiState.Error(errorStringResource = R.string.timeout_error)
                return
            }
            //network error code
            uiState.value = UiState.Error(errorStringResource = R.string.unknown_error)
        }
        result.isUnknownError -> {
            uiState.value = UiState.Error(errorStringResource = R.string.unknown_error)
        }
    }
}