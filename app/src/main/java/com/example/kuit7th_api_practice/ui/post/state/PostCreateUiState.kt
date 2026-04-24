package com.example.kuit7th_api_practice.ui.post.state

import com.example.kuit7th_api_practice.data.model.response.PostResponse

// ?낅젰媛?FormState)怨?泥섎━ ?곹깭(UiState)瑜??섎늻硫?// ?띿뒪?명븘??媛믨낵 ???以??먮윭 ?곹깭瑜??욎? ?딄퀬 ?ㅻ챸?????덉뒿?덈떎.
sealed interface PostCreateUiState {
    data object Idle : PostCreateUiState
    data object UploadingImage : PostCreateUiState
    data object Saving : PostCreateUiState
    data class Success(val post: PostResponse) : PostCreateUiState
    data class Error(val message: String) : PostCreateUiState
}
