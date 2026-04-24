package com.example.kuit7th_api_practice.ui.post.state

import com.example.kuit7th_api_practice.data.model.response.PostResponse

sealed interface PostDetailUiState {
    data object Loading : PostDetailUiState
    data class Success(val post: PostResponse) : PostDetailUiState
    data class Error(val message: String) : PostDetailUiState
}
