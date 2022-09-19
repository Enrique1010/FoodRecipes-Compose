package com.erapps.foodrecipesapp.ui.screens.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erapps.foodrecipesapp.data.models.Meal
import com.erapps.foodrecipesapp.data.source.DetailsRepository
import com.erapps.foodrecipesapp.ui.shared.UiState
import com.erapps.foodrecipesapp.ui.shared.mapResultToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    private val _uiState = mutableStateOf<UiState?>(null)
    val uiState: State<UiState?> = _uiState

    private val _ingredientsList = mutableStateOf<MutableList<String?>>(mutableListOf())
    val ingredientsList: State<List<String?>> = _ingredientsList

    val ingredientsNames = mutableStateListOf<String?>()

    val backGroundColorByImage = mutableStateOf(Color(4294934101)) //primary color

    fun getRecipeById(id: String) = viewModelScope.launch {

        detailsRepository.getRecipeById(id).collect { result ->
            mapResultToUiState(result, _uiState) { response ->
                val meal = response.meals?.get(0)

                meal?.let {
                    _ingredientsList.value = createIngredientsList(it).toMutableList()
                    _uiState.value = UiState.Success(it)
                }
            }
        }
    }

    private fun createIngredientsList(meal: Meal): List<String> {
        val listOfIngredients = mutableListOf<String?>()
        val listOfMeasures = mutableListOf<String?>()

        meal.javaClass.declaredFields.forEach { field ->
            if (field.name.contains("strIngredient")) {
                field.isAccessible = true
                listOfIngredients.add(field.get(meal)?.toString())
            }
            if (field.name.contains("strMeasure")) {
                field.isAccessible = true
                listOfMeasures.add(field.get(meal)?.toString())
            }
        }

        ingredientsNames.addAll(listOfIngredients.filter { it!!.isNotEmpty() && it.isNotBlank() })
        return listOfMeasures.zip(listOfIngredients) { measure, ingredient -> "$measure $ingredient" }
            .filter { it.isNotEmpty() && it.isNotBlank() }
    }

}