package com.erapps.foodrecipesapp.data.source.remote

import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.api.TheMealDBApiService
import com.erapps.foodrecipesapp.data.models.GetRecipesByCategoryResponse
import com.erapps.foodrecipesapp.utils.mapResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CategoryRemoteDataSource {
    fun getRecipesByCategory(category: String): Flow<Result<GetRecipesByCategoryResponse, *>>
}

class CategoryRemoteDataSourceImp @Inject constructor(
    private val theMealDBApiService: TheMealDBApiService
) : CategoryRemoteDataSource {

    override fun getRecipesByCategory(category: String): Flow<Result<GetRecipesByCategoryResponse, *>> =
        mapResponse(Dispatchers.IO) {
            theMealDBApiService.getRecipesByCategory(category)
        }
}