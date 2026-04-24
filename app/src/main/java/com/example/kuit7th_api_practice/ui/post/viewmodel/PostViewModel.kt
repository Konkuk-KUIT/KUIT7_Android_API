package com.example.kuit7th_api_practice.ui.post.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kuit7th_api_practice.data.mock.PostLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postLocalDataSource: PostLocalDataSource
) : ViewModel() {

    // TODO: 목록 화면 상태 만들기
    // 예) var postListUiState by mutableStateOf<PostListUiState>(...)

    // TODO: 상세 화면 상태 만들기

    // TODO: 작성 화면 UiState / FormState 만들기

    // TODO: 수정 화면 UiState / FormState 만들기

    // TODO: getPosts(), getPostDetail() 구현하기

    // TODO: createPost(), updatePost(), deletePost() 구현하기

    // TODO: 이미지 선택 상태 처리 함수 만들기
}
