package com.example.kuit7th_api_practice.data.repositoryimpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.kuit7th_api_practice.data.repository.PostDraftRepository
import com.example.kuit7th_api_practice.ui.post.state.PostCreateFormState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.draftDataStore: DataStore<Preferences> by preferencesDataStore(name = "post_draft")

class PostDraftRepositoryimpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PostDraftRepository {

    private object PreferencesKeys {
        val AUTHOR = stringPreferencesKey("draft_author")
        val TITLE = stringPreferencesKey("draft_title")
        val CONTENT = stringPreferencesKey("draft_content")
        val IMAGE_URI = stringPreferencesKey("draft_image_uri")
    }

    override suspend fun saveDraft(formState: PostCreateFormState) {
        context.draftDataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTHOR] = formState.author
            preferences[PreferencesKeys.TITLE] = formState.title
            preferences[PreferencesKeys.CONTENT] = formState.content
            preferences[PreferencesKeys.IMAGE_URI] = formState.selectedImageUri ?: ""
        }
    }

    override fun getDraft(): Flow<PostCreateFormState> {
        return context.draftDataStore.data.map { preferences ->
            PostCreateFormState(
                author = preferences[PreferencesKeys.AUTHOR] ?: "",
                title = preferences[PreferencesKeys.TITLE] ?: "",
                content = preferences[PreferencesKeys.CONTENT] ?: "",
                selectedImageUri = preferences[PreferencesKeys.IMAGE_URI]?.takeIf { it.isNotEmpty() }
            )
        }
    }

    override suspend fun clearDraft() {
        context.draftDataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.AUTHOR)
            preferences.remove(PreferencesKeys.TITLE)
            preferences.remove(PreferencesKeys.CONTENT)
            preferences.remove(PreferencesKeys.IMAGE_URI)
        }
    }
}
