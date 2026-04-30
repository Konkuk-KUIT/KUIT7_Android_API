package com.example.kuit7th_api_practice.ui.post.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kuit7th_api_practice.data.mock.PostLocalDataSource
import com.example.kuit7th_api_practice.data.model.request.PostCreateRequest
import com.example.kuit7th_api_practice.ui.post.state.PostCreateFormState
import com.example.kuit7th_api_practice.ui.post.state.PostCreateUiState
import com.example.kuit7th_api_practice.ui.post.state.PostDetailUiState
import com.example.kuit7th_api_practice.ui.post.state.PostEditFormState
import com.example.kuit7th_api_practice.ui.post.state.PostEditUiState
import com.example.kuit7th_api_practice.ui.post.state.PostListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.runCatching

@HiltViewModel      //hilt가 이 뷰모델 생성 및 생성자에 있는 의존성 자동주입
class PostViewModel @Inject constructor(
    private val postLocalDataSource: PostLocalDataSource
) : ViewModel() {

    // TODO: 목록 화면 상태 만들기
    var postListUiState by mutableStateOf<PostListUiState>(PostListUiState.Loading)
        private set

    // TODO: 상세 화면 상태 만들기
    var postDetailUiState by mutableStateOf<PostDetailUiState>(PostDetailUiState.Loading)
        private set

    // TODO: 작성 화면 UiState / FormState 만들기
    var postCreateFormState by mutableStateOf(PostCreateFormState())
        private set

    var postCreateUiState by mutableStateOf<PostCreateUiState>(PostCreateUiState.Idle)
        private set

    // TODO: 수정 화면 UiState / FormState 만들기
    var postEditFormState by mutableStateOf(PostEditFormState())
        private set

    var postEditUiState by mutableStateOf<PostEditUiState>(PostEditUiState.Loading)
        private set

    var isUploading by mutableStateOf(false)
        private set

    // TODO: getPosts(), getPostDetail() 구현하기
    fun getPosts() {        //게시글 목록 갱신
        viewModelScope.launch {      //코루틴 실행
            postListUiState = PostListUiState.Loading
            runCatching {
                postLocalDataSource.getPosts()      //repository에 데이터 요청
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
                    val message = "게시글을 찾을수 없습니다."
                    postDetailUiState = PostDetailUiState.Error(message)
                    postEditUiState = PostEditUiState.Error(message)
                } else {
                    postDetailUiState = PostDetailUiState.Success(post)
                    postEditUiState = PostEditUiState.Ready(post)
                    initializeEditForm(post.id, post.title, post.content, post.imageUrl)
                }
            }.onFailure { error ->
                val message = error.message ?: "게시글을 불러오지 못했습니다"
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
    fun createPost() {
        val formstate = postCreateFormState
        viewModelScope.launch {
            postCreateUiState = PostCreateUiState.Saving

            runCatching {
                postLocalDataSource.createPost(
                    authorName = formstate.author,
                    request = PostCreateRequest(
                        title = formstate.title,
                        content = formstate.content,
                        imageUrl = formstate.selectedImageUri
                    )
                )
            }.onSuccess { post ->
                postCreateUiState = PostCreateUiState.Success(post)
                postCreateFormState = PostCreateFormState()
            }.onFailure { error ->
                postCreateUiState = PostCreateUiState.Error(
                    error.message ?: "게시글 작성에 실패했습니다."
                )
            }
        }
    }


    fun updatePost(postId: Long) {
        val formstate = postEditFormState
        viewModelScope.launch {
            postEditUiState = PostEditUiState.Saving

            runCatching {
                postLocalDataSource.updatePost(
                    postId = postId,
                    request = PostCreateRequest(
                        title = formstate.title,
                        content = formstate.content,
                        imageUrl = formstate.selectedImageUri ?: formstate.originalImageUrl
                    )
                )
            }.onSuccess { post ->
                if (post == null) {
                    postEditUiState = PostEditUiState.Error("게시글을 찾을수 없습니다.")
                } else {
                    postEditUiState = PostEditUiState.Success(post)
                    postDetailUiState = PostDetailUiState.Success(post)
                    initializeEditForm(
                        postId = post.id,
                        title = post.title,
                        content = post.content,
                        imageUrl = post.imageUrl,
                        force = true
                    )
                }
            }.onFailure { error ->
                postEditUiState = PostEditUiState.Error(
                    error.message ?: "게시글 수정에 실패했습니다."
                )
            }
        }
    }

    fun deletePost(postId: Long) {
        viewModelScope.launch {
            runCatching {
                postLocalDataSource.deletePost(postId)
            }.onSuccess { delete ->
                if (delete == true) {
                    postDetailUiState = PostDetailUiState.Error("삭제된 게시글입니다.")
                } else {
                    postDetailUiState = PostDetailUiState.Error("삭제 실패.")
                }

            }.onFailure { error ->
                postDetailUiState = PostDetailUiState.Error(
                    error.message ?: "삭제에 실패했습니다."
                )
            }
        }
    }

    fun changeCreateImageUri(uri: String?) {
        postCreateFormState = postCreateFormState.copy(selectedImageUri = uri)
    }

    fun changeCreateAuthor(author: String) {
        postCreateFormState = postCreateFormState.copy(author = author)
    }

    fun changeCreateTitle(title: String) {
        postCreateFormState = postCreateFormState.copy(title = title)
    }

    fun changeCreateContent(content: String) {
        postCreateFormState = postCreateFormState.copy(content = content)
    }

    fun changeEditSImageUri(uri: String?) {
        postEditFormState = postEditFormState.copy(selectedImageUri = uri)
    }

    fun changeEditOImageUrl(url: String?) {
        postEditFormState = postEditFormState.copy(originalImageUrl = url)
    }

    fun changeEditTitle(title: String) {
        postEditFormState = postEditFormState.copy(title = title)
    }

    fun changeEditContent(content: String) {
        postEditFormState = postEditFormState.copy(content = content)
    }
    // TODO: 이미지 선택 상태 처리 함수 만들기(선택)
}
