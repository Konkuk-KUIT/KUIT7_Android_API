package com.example.kuit7th_api_practice.ui.post.state

import com.example.kuit7th_api_practice.domain.repository.model.Post
import kotlinx.coroutines.flow.Flow

sealed interface PostDetailUiState {
    data object Loading : PostDetailUiState
    data class Success(val post: Post) : PostDetailUiState
    data class Error(val message: String) : PostDetailUiState
}
