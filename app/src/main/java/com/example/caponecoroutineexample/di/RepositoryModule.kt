package com.example.caponecoroutineexample.di

import com.example.caponecoroutineexample.data.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesDataRepository(): DataRepository {
        return DataRepository()
    }

}