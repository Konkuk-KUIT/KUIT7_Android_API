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

    @Binds      //구현체(InMemoryMockPostDataSource)를 인터페이스(PostLocalDataSource)와 연결하는 역할
    @Singleton
    abstract fun bindPostLocalDataSource(
        dataSource: InMemoryMockPostDataSource
    ): PostLocalDataSource
}
