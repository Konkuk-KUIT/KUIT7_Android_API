package com.example.kuit7th_api_practice.ui.post.state

import com.example.kuit7th_api_practice.data.model.response.PostResponse

sealed interface PostEditUiState {
    data object Loading : PostEditUiState
    data class Ready(val post: PostResponse) : PostEditUiState
    data object Saving : PostEditUiState
    data class Success(val post: PostResponse) : PostEditUiState
    data class Error(val message: String) : PostEditUiState
}
