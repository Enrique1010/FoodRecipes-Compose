package com.erapps.foodrecipesapp.di

import com.erapps.foodrecipesapp.data.source.CategoryRepository
import com.erapps.foodrecipesapp.data.source.CategoryRepositoryImp
import com.erapps.foodrecipesapp.data.source.remote.CategoryRemoteDataSource
import com.erapps.foodrecipesapp.data.source.remote.CategoryRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryModule {

    @Singleton
    @Binds
    abstract fun provideCategoryRemoteDataSource(
        categoryRemoteDataSourceImp: CategoryRemoteDataSourceImp
    ): CategoryRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideCategoryRepository(
        categoryRepositoryImp: CategoryRepositoryImp
    ): CategoryRepository
}