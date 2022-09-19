package com.erapps.foodrecipesapp.data.source

import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.models.SearchRecipesResponse
import com.erapps.foodrecipesapp.data.source.remote.DetailsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface DetailsRepository {
    fun getRecipeById(id: String): Flow<Result<SearchRecipesResponse, *>>
}

class DetailsRepositoryImp @Inject constructor(
    private val dataSource: DetailsRemoteDataSource
): DetailsRepository {

    override fun getRecipeById(id: String): Flow<Result<SearchRecipesResponse, *>> {
        return dataSource.getRecipeById(id).flowOn(Dispatchers.Default)
    }
}