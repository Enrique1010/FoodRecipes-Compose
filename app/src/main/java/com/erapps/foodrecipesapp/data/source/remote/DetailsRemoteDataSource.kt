package com.erapps.foodrecipesapp.data.source.remote

import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.api.TheMealDBApiService
import com.erapps.foodrecipesapp.data.models.SearchRecipesResponse
import com.erapps.foodrecipesapp.utils.mapResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DetailsRemoteDataSource {
    fun getRecipeById(id: String): Flow<Result<SearchRecipesResponse, *>>
}

class DetailsRemoteDataSourceImp @Inject constructor(
    private val theMealDBApiService: TheMealDBApiService
) : DetailsRemoteDataSource {

    override fun getRecipeById(id: String): Flow<Result<SearchRecipesResponse, *>> =
        mapResponse(Dispatchers.IO) {
            theMealDBApiService.getRecipeById(id)
        }
}