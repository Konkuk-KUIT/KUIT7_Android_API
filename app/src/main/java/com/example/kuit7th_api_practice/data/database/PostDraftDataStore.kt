package com.example.kuit7th_api_practice.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostDraftDataStore @Inject constructor(
    private val context: Context
) {
    companion object {
        private val Context.postDraftDataStore: DataStore<Preferences> by preferencesDataStore(
            name = "post_draft_preferences"
        )

        private val KEY_AUTHOR = stringPreferencesKey("draft_author")
        private val KEY_TITLE = stringPreferencesKey("draft_title")
        private val KEY_CONTENT = stringPreferencesKey("draft_content")
    }

     //작성자 정보 저장
    suspend fun saveAuthor(author: String) {
        context.postDraftDataStore.edit { preferences ->
            preferences[KEY_AUTHOR] = author
        }
    }

     //제목 저장
    suspend fun saveTitle(title: String) {
        context.postDraftDataStore.edit { preferences ->
            preferences[KEY_TITLE] = title
        }
    }

     //내용 저장
    suspend fun saveContent(content: String) {
        context.postDraftDataStore.edit { preferences ->
            preferences[KEY_CONTENT] = content
        }
    }

     //작성자, 제목, 내용 한 번에 저장
    suspend fun saveDraft(author: String, title: String, content: String) {
        context.postDraftDataStore.edit { preferences ->
            preferences[KEY_AUTHOR] = author
            preferences[KEY_TITLE] = title
            preferences[KEY_CONTENT] = content
        }
    }

     //저장된 작성자 정보를 가져옴
    suspend fun getAuthor(): String {
        return context.postDraftDataStore.data.first()[KEY_AUTHOR] ?: ""
    }

     //저장된 제목을 가져옴
    suspend fun getTitle(): String {
        return context.postDraftDataStore.data.first()[KEY_TITLE] ?: ""
    }

    // 저장된 내용을 가져옴
    suspend fun getContent(): String {
        return context.postDraftDataStore.data.first()[KEY_CONTENT] ?: ""
    }

    // 저장된 작성자, 제목, 내용을 한 번에 가져옴
    suspend fun getDraft(): Triple<String, String, String> {
        val preferences = context.postDraftDataStore.data.first()
        return Triple(
            preferences[KEY_AUTHOR] ?: "",
            preferences[KEY_TITLE] ?: "",
            preferences[KEY_CONTENT] ?: ""
        )
    }

     // 저장된 임시 데이터 모두 삭제
    suspend fun clearDraft() {
        context.postDraftDataStore.edit { preferences ->
            preferences.remove(KEY_AUTHOR)
            preferences.remove(KEY_TITLE)
            preferences.remove(KEY_CONTENT)
        }
    }
}