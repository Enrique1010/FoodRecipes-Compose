package com.erapps.foodrecipesapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erapps.foodrecipesapp.R
import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.source.SearchDefaultRepository
import com.erapps.foodrecipesapp.ui.shared.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchDefaultRepository: SearchDefaultRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    fun searchRecipesByName(name: String) = viewModelScope.launch {

        searchDefaultRepository.searchRecipesByName(name).collect { result ->
            when (result) {
                Result.Loading -> {
                    _uiState.update { UiState.Loading }
                }
                is Result.Error -> {
                    when {
                        result.isApiError -> {
                            if (result.code in 500..599) {
                                _uiState.update { UiState.Error(errorStringResource = R.string.error_server) }
                                return@collect
                            }
                            _uiState.update { UiState.Error(errorMessage = result.exception?.message!!) }
                        }
                        result.isNetworkError -> {
                            if (result.exception is SocketTimeoutException) {
                                _uiState.update { UiState.Error(errorStringResource = R.string.timeout_error) }
                                return@collect
                            }
                            //network error code
                            _uiState.update { UiState.Error(errorStringResource = R.string.unknown_error) }
                        }
                        result.isUnknownError -> {
                            _uiState.update { UiState.Error(errorStringResource = R.string.unknown_error) }
                        }
                    }
                }
                is Result.Success -> {
                    if (result.data?.meals?.isEmpty() == true) {
                        _uiState.update { UiState.Empty }
                        return@collect
                    }
                    //success code
                    _uiState.update { UiState.Success(result.data?.meals) }
                }
            }
        }
    }

}