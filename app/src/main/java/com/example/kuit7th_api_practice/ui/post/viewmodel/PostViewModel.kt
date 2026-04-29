package com.example.kuit7th_api_practice.ui.post.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kuit7th_api_practice.data.mock.PostLocalDataSource
import com.example.kuit7th_api_practice.ui.post.state.PostCreateFormState
import com.example.kuit7th_api_practice.ui.post.state.PostDetailUiState
import com.example.kuit7th_api_practice.ui.post.state.PostEditFormState
import com.example.kuit7th_api_practice.ui.post.state.PostEditUiState
import com.example.kuit7th_api_practice.ui.post.state.PostListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postLocalDataSource: PostLocalDataSource
) : ViewModel() {

    // TODO: 목록 화면 상태 만들기
    var postListUiState by mutableStateOf<PostListUiState>(PostListUiState.Loading)
        private set
    var postDetailUiState by mutableStateOf<PostDetailUiState>(PostDetailUiState.Loading)
        private set
    var postEditUiState by mutableStateOf<PostEditUiState>(PostEditUiState.Loading)
        private set

    var postCreateFromState by mutableStateOf(PostCreateFormState())
        private set
    var postEditFormState by mutableStateOf(PostEditFormState())
        private set
    var isUploading by mutableStateOf(false)
        private set

    fun getPosts() {
        viewModelScope.launch {
            postListUiState = PostListUiState.Loading
            runCatching {
                postLocalDataSource.getPosts()
            }.onSuccess { posts ->
                postListUiState = PostListUiState.Success(posts)
            }.onFailure { error ->
                postListUiState = PostListUiState.Error(
                    error.message ?: "게시글 목록을 불러오지 못했습니다."
                )
            }
        }
    }

    fun getPostDetail(postId: Long) {
        viewModelScope.launch {
            postDetailUiState = PostDetailUiState.Loading
            postEditUiState = PostEditUiState.Loading

            runCatching {
                postLocalDataSource.getPostDetail(postId)
            }.onSuccess { post ->
                if (post == null) {
                    val message = "게시글을 찾을 수 없습니다."
                    postDetailUiState = PostDetailUiState.Error(message)
                    postEditUiState = PostEditUiState.Error(message)
                } else {
                    postDetailUiState = PostDetailUiState.Success(post)
                    postEditUiState = PostEditUiState.Ready(post)
                    initializeEditForm(post.id, post.title, post.content, post.imageUrl)
                }
            }.onFailure { error ->
                val message = error.message ?: "게시글을 불러오지 못했습니다."
                postDetailUiState = PostDetailUiState.Error(message)
                postEditUiState = PostEditUiState.Error(message)
            }
        }
    }

    private fun initializeEditForm(
        postId: Long,
        title: String,
        content: String,
        imageUrl: String?,
        force: Boolean = false
    ) {
        if (!force && postEditFormState.initializedPostId == postId) return

        postEditFormState = PostEditFormState(
            title = title,
            content = content,
            originalImageUrl = imageUrl,
            selectedImageUri = null,
            initializedPostId = postId
        )
    }
    // TODO: createPost(), updatePost(), deletePost() 구현하기

    // TODO: 이미지 선택 상태 처리 함수 만들기
}
