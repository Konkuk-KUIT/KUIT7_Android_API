package com.example.kuit7th_api_practice.di

import com.example.kuit7th_api_practice.data.mock.InMemoryMockPostDataSource
import com.example.kuit7th_api_practice.data.mock.PostLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PostDataModule {

    @Binds
    @Singleton
    abstract fun bindPostLocalDataSource(
        dataSource: InMemoryMockPostDataSource
    ): PostLocalDataSource
}
