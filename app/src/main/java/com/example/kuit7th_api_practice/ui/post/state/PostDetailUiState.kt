package com.example.kuit7th_api_practice.ui.post.state

import com.example.kuit7th_api_practice.data.model.response.PostResponse

// ?곸꽭 ?붾㈃??媛숈? 諛⑹떇?쇰줈 ?곹깭瑜??섎늻硫?// ?곗씠?곕? 遺덈윭?ㅻ뒗 以묒씤吏, ?깃났?덈뒗吏, ?ㅽ뙣?덈뒗吏瑜?遺꾧린?댁꽌 洹몃┫ ???덉뒿?덈떎.
sealed interface PostDetailUiState {
    data object Loading : PostDetailUiState
    data class Success(val post: PostResponse) : PostDetailUiState
    data class Error(val message: String) : PostDetailUiState
}
