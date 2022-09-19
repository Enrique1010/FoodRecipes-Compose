package com.erapps.foodrecipesapp.data.source

import com.erapps.foodrecipesapp.data.Result
import com.erapps.foodrecipesapp.data.models.GetRecipesByCategoryResponse
import com.erapps.foodrecipesapp.data.source.remote.CategoryRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface CategoryRepository {
    fun getRecipesByCategory(category: String): Flow<Result<GetRecipesByCategoryResponse, *>>
}

class CategoryRepositoryImp @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource
): CategoryRepository {

    override fun getRecipesByCategory(category: String): Flow<Result<GetRecipesByCategoryResponse, *>> {
        return categoryRemoteDataSource.getRecipesByCategory(category).flowOn(Dispatchers.Default)
    }

}