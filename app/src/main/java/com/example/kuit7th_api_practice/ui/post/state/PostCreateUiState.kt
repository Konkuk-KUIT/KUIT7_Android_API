package com.example.kuit7th_api_practice.ui.post.state

import com.example.kuit7th_api_practice.data.model.response.PostResponse

sealed interface PostCreateUiState {
    data object Idle : PostCreateUiState
    data object UploadingImage : PostCreateUiState
    data object Saving : PostCreateUiState
    data class Success(val post: PostResponse) : PostCreateUiState
    data class Error(val message: String) : PostCreateUiState
}
