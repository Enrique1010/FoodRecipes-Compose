package com.erapps.foodrecipesapp.data.source.remote

import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.api.TheMealDBApiService
import com.erapps.foodrecipesapp.data.models.SearchRecipesResponse
import com.erapps.foodrecipesapp.utils.mapResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchRemoteDataSource {
    fun searchRecipesByName(name: String): Flow<Result<SearchRecipesResponse, *>>
}

class SearchRemoteDataSourceImp @Inject constructor(
    private val theMealDBApiService: TheMealDBApiService
) : SearchRemoteDataSource {

    override fun searchRecipesByName(
        name: String
    ): Flow<Result<SearchRecipesResponse, *>> = mapResponse(Dispatchers.IO) {
        theMealDBApiService.searchRecipesByName(name)
    }
}