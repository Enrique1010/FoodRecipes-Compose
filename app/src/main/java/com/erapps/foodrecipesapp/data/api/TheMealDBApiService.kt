package com.erapps.foodrecipesapp.data.api

import com.erapps.foodrecipesapp.data.models.GetAreasResponse
import com.erapps.foodrecipesapp.data.models.GetCategoriesResponse
import com.erapps.foodrecipesapp.data.models.GetRecipesByCategoryResponse
import com.erapps.foodrecipesapp.data.models.SearchRecipesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMealDBApiService {

    @GET("search.php")
    suspend fun searchRecipesByName(
        @Query("s") query: String
    ): SearchRecipesResponse

    @GET("lookup.php/{id}")
    suspend fun getRecipeById(
        @Path("id") id: String
    ): SearchRecipesResponse

    @GET("random.php")
    suspend fun getRandomRecipe(): SearchRecipesResponse

    @GET("filter.php")
    suspend fun getRecipesByCategory(
        @Query("c") category: String
    ): GetRecipesByCategoryResponse

    @GET("list.php")
    suspend fun getFoodCategories(
        @Query("c") query: String = "list"
    ): GetCategoriesResponse

    @GET("list.php")
    suspend fun getFoodAreas(
        @Query("a") query: String = "list"
    ): GetAreasResponse
}