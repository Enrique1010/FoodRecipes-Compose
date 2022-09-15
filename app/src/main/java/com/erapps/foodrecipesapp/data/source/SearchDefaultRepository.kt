package com.erapps.foodrecipesapp.data.source

import com.erapps.foodrecipesapp.data.models.SearchRecipesResponse
import com.erapps.foodrecipesapp.data.source.remote.SearchRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.erapps.foodrecipesapp.data.Result

interface SearchDefaultRepository {
    fun searchRecipesByName(name: String): Flow<Result<SearchRecipesResponse, *>>
}

class SearchDefaultRepositoryImp @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
): SearchDefaultRepository {

    override fun searchRecipesByName(name: String): Flow<Result<SearchRecipesResponse, *>> {
        return searchRemoteDataSource.searchRecipesByName(name).flowOn(Dispatchers.Default)
    }

}