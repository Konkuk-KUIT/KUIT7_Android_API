package com.example.kuit7th_api_practice.data.repository

import com.example.kuit7th_api_practice.ui.post.state.PostCreateFormState
import kotlinx.coroutines.flow.Flow

interface PostDraftRepository {
    suspend fun saveDraft(formState: PostCreateFormState)
    fun getDraft(): Flow<PostCreateFormState>
    suspend fun clearDraft()
}
