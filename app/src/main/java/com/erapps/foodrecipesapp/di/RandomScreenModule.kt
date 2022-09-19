package com.erapps.foodrecipesapp.di

import com.erapps.foodrecipesapp.data.source.RandomScreenRepository
import com.erapps.foodrecipesapp.data.source.RandomScreenRepositoryImp
import com.erapps.foodrecipesapp.data.source.remote.RandomScreenRemoteDataSource
import com.erapps.foodrecipesapp.data.source.remote.RandomScreenRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RandomScreenModule {

    @Binds
    @Singleton
    abstract fun provideRandomScreenRemoteDataSource(
        randomScreenRemoteDataSourceImp: RandomScreenRemoteDataSourceImp
    ): RandomScreenRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideRandomScreenRepository(
        randomScreenRepositoryImp: RandomScreenRepositoryImp
    ): RandomScreenRepository
}