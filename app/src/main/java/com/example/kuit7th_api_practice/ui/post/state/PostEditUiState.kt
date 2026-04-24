package com.example.kuit7th_api_practice.ui.post.state

import com.example.kuit7th_api_practice.data.model.response.PostResponse

// ?섏젙 ?붾㈃? 湲곗〈 寃뚯떆湲??癒쇱? ?쎌뼱????섎?濡?Ready ?곹깭瑜??곕줈 ?〓땲??
// ???곹깭媛 ?덉뼱???섏젙 ?쇱쓽 珥덇린媛믪쓣 ?몄젣 ?ｌ쓣吏 遺꾨챸?섍쾶 ?ㅻ챸?????덉뒿?덈떎.
sealed interface PostEditUiState {
    data object Loading : PostEditUiState
    data class Ready(val post: PostResponse) : PostEditUiState
    data object Saving : PostEditUiState
    data class Success(val post: PostResponse) : PostEditUiState
    data class Error(val message: String) : PostEditUiState
}
