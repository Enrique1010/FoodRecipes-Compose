package com.erapps.foodrecipesapp.data.source

import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.models.SearchRecipesResponse
import com.erapps.foodrecipesapp.data.source.remote.RandomScreenRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface RandomScreenRepository {
    fun getRandomRecipe(): Flow<Result<SearchRecipesResponse, *>>
}

class RandomScreenRepositoryImp @Inject constructor(
    private val randomScreenRemoteDataSource: RandomScreenRemoteDataSource
) : RandomScreenRepository {

    override fun getRandomRecipe(): Flow<Result<SearchRecipesResponse, *>> {
        return randomScreenRemoteDataSource.getRandomRecipe().flowOn(Dispatchers.Default)
    }

}