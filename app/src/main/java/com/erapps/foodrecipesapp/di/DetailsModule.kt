package com.erapps.foodrecipesapp.di

import com.erapps.foodrecipesapp.data.source.DetailsRepository
import com.erapps.foodrecipesapp.data.source.DetailsRepositoryImp
import com.erapps.foodrecipesapp.data.source.remote.DetailsRemoteDataSource
import com.erapps.foodrecipesapp.data.source.remote.DetailsRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailsModule {

    @Singleton
    @Binds
    abstract fun provideDetailsRemoteDataSource(
        detailsRemoteDataSourceImp: DetailsRemoteDataSourceImp
    ): DetailsRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideDetailsRepository(
        detailsRepositoryImp: DetailsRepositoryImp
    ): DetailsRepository
}