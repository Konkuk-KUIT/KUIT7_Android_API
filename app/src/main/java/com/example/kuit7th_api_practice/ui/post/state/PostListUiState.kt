package com.example.kuit7th_api_practice.ui.post.state

import com.example.kuit7th_api_practice.data.model.response.PostResponse

sealed interface PostListUiState {
    data object Loading : PostListUiState
    data class Success(val posts: List<PostResponse>) : PostListUiState
    data class Error(val message: String) : PostListUiState
}
