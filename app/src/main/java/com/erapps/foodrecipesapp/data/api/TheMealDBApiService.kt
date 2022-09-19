package com.erapps.foodrecipesapp.data.api

import com.erapps.foodrecipesapp.data.models.GetCategoriesResponse
import com.erapps.foodrecipesapp.data.models.GetRecipesByCategoryResponse
import com.erapps.foodrecipesapp.data.models.SearchRecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDBApiService {

    @GET("search.php")
    suspend fun searchRecipesByName(
        @Query("s") query: String
    ): NetworkResponse<SearchRecipesResponse, *>

    @GET("lookup.php")
    suspend fun getRecipeById(
        @Query("i") id: String
    ): NetworkResponse<SearchRecipesResponse, *>

    @GET("random.php")
    suspend fun getRandomRecipe(): NetworkResponse<SearchRecipesResponse, *>

    @GET("filter.php")
    suspend fun getRecipesByCategory(
        @Query("c") category: String
    ): NetworkResponse<GetRecipesByCategoryResponse, *>

    @GET("list.php")
    suspend fun getFoodCategories(
        @Query("c") query: String = "list"
    ): NetworkResponse<GetCategoriesResponse, *>
}