package com.erapps.foodrecipesapp.data.source.remote

import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.api.TheMealDBApiService
import com.erapps.foodrecipesapp.data.models.SearchRecipesResponse
import com.erapps.foodrecipesapp.utils.mapResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RandomScreenRemoteDataSource {
    fun getRandomRecipe(): Flow<Result<SearchRecipesResponse, *>>
}

class RandomScreenRemoteDataSourceImp @Inject constructor(
    private val theMealDBApiService: TheMealDBApiService
) : RandomScreenRemoteDataSource {

    override fun getRandomRecipe(): Flow<Result<SearchRecipesResponse, *>> =
        mapResponse(Dispatchers.IO) {
            theMealDBApiService.getRandomRecipe()
        }
}