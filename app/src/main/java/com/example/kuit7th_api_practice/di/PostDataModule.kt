package com.example.kuit7th_api_practice.di

import com.example.kuit7th_api_practice.data.mock.InMemoryMockPostDataSource
import com.example.kuit7th_api_practice.data.mock.PostLocalDataSource
import com.example.kuit7th_api_practice.data.repository.FavoriteRepository
import com.example.kuit7th_api_practice.data.repository.temporarySaveRepository
import com.example.kuit7th_api_practice.data.repositoryimpl.FavoriteRepositoryImpl
import com.example.kuit7th_api_practice.data.repositoryimpl.temporarySaveRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        repository: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Binds
    @Singleton//앱 전체에서 하나만 만들어서 재사용
    abstract fun bindTemporarySaveRepository(
        repository: temporarySaveRepositoryImpl
    ): temporarySaveRepository//temporarySaveRepositoryImpl객체를 temporarySaveRepository타입으로 제공
}
