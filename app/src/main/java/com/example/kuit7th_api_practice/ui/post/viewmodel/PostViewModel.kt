package com.example.kuit7th_api_practice.ui.post.viewmodel

import android.R.id.message
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kuit7th_api_practice.data.mock.PostLocalDataSource
import com.example.kuit7th_api_practice.data.model.request.PostCreateRequest
import com.example.kuit7th_api_practice.data.model.response.AuthorResponse
import com.example.kuit7th_api_practice.data.model.response.PostResponse
import com.example.kuit7th_api_practice.ui.post.screen.PostPracticeSampleData.posts
import com.example.kuit7th_api_practice.ui.post.state.PostCreateFormState
import com.example.kuit7th_api_practice.ui.post.state.PostCreateUiState
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

    var postListUiState by mutableStateOf<PostListUiState>(PostListUiState.Loading)
        private set
    var postDetailUiState by mutableStateOf<PostDetailUiState>(PostDetailUiState.Loading)
        private set
    var postEditUiState by mutableStateOf<PostEditUiState>(PostEditUiState.Loading)
        private set
    var postCreateUiState by mutableStateOf<PostCreateUiState>(PostCreateUiState.Error(""))
        private set
    var postCreateFormState by mutableStateOf(PostCreateFormState())
        private set
    var postEditFormState by mutableStateOf(PostEditFormState())
        private set
    var isUploading by mutableStateOf(false)
        private set

    fun getPost() {
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
                    val message = "게시글 찾을 수 없습니다."
                    postDetailUiState = PostDetailUiState.Error(message)
                    postEditUiState = PostEditUiState.Error(message)
                } else {
                    postDetailUiState = PostDetailUiState.Success(post)
                    postEditUiState = PostEditUiState.Ready(post)
                    initializeEditForm(post.id, post.title, post.content, post.imageUrl)
                }
            }.onFailure { e ->
                val message = e.message ?: "게시글을 불러오지 못했습니다."
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

    fun createPost() {
        viewModelScope.launch {
            runCatching {
                postLocalDataSource.createPost(
                    authorName = postCreateFormState.author,
                    request = PostCreateRequest(
                        title = postCreateFormState.title,
                        content = postCreateFormState.content,
                        imageUrl = postCreateFormState.selectedImageUri
                    )
                )
            }.onSuccess { post ->
                postCreateUiState = PostCreateUiState.Success(post)
                getPost()
            }.onFailure { error ->
                postCreateUiState = PostCreateUiState.Error(error.message ?: "게시글 작성에 실패했습니다")
            }
        }
    }

    fun updatePost(postId: Long) {
        viewModelScope.launch {

            runCatching {
                postEditUiState = PostEditUiState.Saving

                postLocalDataSource.updatePost(
                    postId = postId,
                    request = PostCreateRequest(
                        title = postEditFormState.title,      // postEditFormState 사용!
                        content = postEditFormState.content,
                        imageUrl = postEditFormState.selectedImageUri ?: postEditFormState.originalImageUrl
                    )
                )
            }.onSuccess { post ->
                if (post == null) {
                    postEditUiState = PostEditUiState.Error("게시글을 찾을 수 없습니다")
                } else {
                    postEditUiState = PostEditUiState.Success(post)
                    postDetailUiState = PostDetailUiState.Success(post)
                    getPost()
                }
            }.onFailure { error ->
                postEditUiState = PostEditUiState.Error(error.message ?: "게시글 수정에 실패했습니다")
            }
        }
    }

    fun deletePost(postId: Long) {
        viewModelScope.launch {
            runCatching {
                postLocalDataSource.deletePost(postId)
            }.onSuccess {
                getPost()
            }
        }
    }
    fun onEditTitleChange(title: String) {
        postEditFormState = postEditFormState.copy(title = title)
    }

    fun onEditContentChange(content: String) {
        postEditFormState = postEditFormState.copy(content = content)
    }

    fun onEditImageChange(uri: String?) {
        postEditFormState = postEditFormState.copy(selectedImageUri = uri)
    }
    fun onCreateTitleChange(title: String) {
        postCreateFormState = postCreateFormState.copy(title = title)
    }
    fun onCreateAuthorChange(author: String) {
        postCreateFormState = postCreateFormState.copy(author = author)
    }

    fun onCreateContentChange(content: String) {
        postCreateFormState = postCreateFormState.copy(content = content)
    }

}
