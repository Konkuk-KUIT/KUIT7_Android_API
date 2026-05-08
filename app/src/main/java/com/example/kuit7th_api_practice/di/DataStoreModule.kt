/*
package com.example.kuit7th_api_practice.di

import android.content.Context
import com.example.kuit7th_api_practice.data.datastore.PostDraftDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePostDraftDataStore(
        @ApplicationContext context: Context
    ): PostDraftDataStore {
        return PostDraftDataStore(context)
    }
}*/
