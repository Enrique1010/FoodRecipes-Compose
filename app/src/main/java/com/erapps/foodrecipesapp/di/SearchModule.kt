package com.erapps.foodrecipesapp.di

import com.erapps.foodrecipesapp.data.source.SearchDefaultRepository
import com.erapps.foodrecipesapp.data.source.SearchDefaultRepositoryImp
import com.erapps.foodrecipesapp.data.source.remote.SearchRemoteDataSource
import com.erapps.foodrecipesapp.data.source.remote.SearchRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {

    @Singleton
    @Binds
    abstract fun provideSearchRemoteDataSource(
        searchRemoteDataSourceImp: SearchRemoteDataSourceImp
    ): SearchRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideSearchDefaultRepository(
        searchDefaultRepositoryImp: SearchDefaultRepositoryImp
    ): SearchDefaultRepository
}