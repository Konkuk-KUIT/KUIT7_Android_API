package com.example.kuit7th_api_practice.ui.post.state

import com.example.kuit7th_api_practice.data.model.response.PostResponse

// UiState???붾㈃??泥섎━ ?곹깭瑜??쒗쁽?⑸땲??
// ?대쾲 二쇱감?먯꽌??濡쒕뵫, ?깃났, ?ㅽ뙣???곕씪 ?ㅻⅨ UI瑜?蹂댁뿬二쇰뒗 ?⑸룄濡??댄빐?섎㈃ 異⑸텇?⑸땲??
sealed interface PostListUiState {
    data object Loading : PostListUiState
    data class Success(val posts: List<PostResponse>) : PostListUiState
    data class Error(val message: String) : PostListUiState
}
