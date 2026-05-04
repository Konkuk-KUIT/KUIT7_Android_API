package com.example.kuit7th_api_practice.data.repositoryimpl

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.kuit7th_api_practice.data.model.response.PostResponse
import com.example.kuit7th_api_practice.data.repository.FavoriteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

val Context.datastore by preferencesDataStore("favorite")

class FavoriteRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
) : FavoriteRepository {
    override suspend fun getFavorite(posts: List<PostResponse>): Map<Long, Boolean> {

        val map = mutableMapOf<Long, Boolean>()

        posts.forEach { post ->
            val key = booleanPreferencesKey("favorite_${post.id}")

            map[post.id] = context.datastore.data.first()[key] ?: false
        }
        return map.toMap()
    }

    override suspend fun getFavorite(id: Long): Boolean {
        val key = booleanPreferencesKey("favorite_${id}")

        return context.datastore.data.first()[key] ?: false
    }

    override suspend fun setFavorite(id: Long) {
        val key = booleanPreferencesKey("favorite_${id}")

        context.datastore.edit {
            it[key] = it[key] != true
        }
    }
}