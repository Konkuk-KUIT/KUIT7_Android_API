package com.example.kuit7th_api_practice.ui.post.viewmodel

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
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postLocalDataSource: PostLocalDataSource
) : ViewModel() {

    // ============================================================
    // 목록 화면
    // ============================================================

    private val _listUiState = MutableStateFlow<PostListUiState>(PostListUiState.Loading)
    val listUiState: StateFlow<PostListUiState> = _listUiState.asStateFlow()

    fun getPosts() {
        viewModelScope.launch {
            _listUiState.value = PostListUiState.Loading
            try {
                val posts = postLocalDataSource.getPosts()
                _listUiState.value = PostListUiState.Success(posts)
            } catch (e: Exception) {
                _listUiState.value = PostListUiState.Error(e.message ?: "알 수 없는 오류")
            }
        }
    }

    // ============================================================
    // 상세 화면
    // ============================================================

    private val _detailUiState = MutableStateFlow<PostDetailUiState>(PostDetailUiState.Loading)
    val detailUiState: StateFlow<PostDetailUiState> = _detailUiState.asStateFlow()

    fun getPostDetail(postId: Long) {
        viewModelScope.launch {
            _detailUiState.value = PostDetailUiState.Loading
            try {
                val post = postLocalDataSource.getPostDetail(postId)
                if (post == null) {
                    _detailUiState.value = PostDetailUiState.Error("게시글을 찾을 수 없습니다")
                } else {
                    _detailUiState.value = PostDetailUiState.Success(post)
                }
            } catch (e: Exception) {
                _detailUiState.value = PostDetailUiState.Error(e.message ?: "알 수 없는 오류")
            }
        }
    }

    // ============================================================
    // 작성 화면
    // ============================================================

    private val _createFormState = MutableStateFlow(PostCreateFormState())
    val createFormState: StateFlow<PostCreateFormState> = _createFormState.asStateFlow()

    private val _createUiState = MutableStateFlow<PostCreateUiState>(PostCreateUiState.Idle)
    val createUiState: StateFlow<PostCreateUiState> = _createUiState.asStateFlow()

    fun updateAuthor(author: String) {
        _createFormState.value = _createFormState.value.copy(author = author)
    }

    fun updateTitle(title: String) {
        _createFormState.value = _createFormState.value.copy(title = title)
    }

    fun updateContent(content: String) {
        _createFormState.value = _createFormState.value.copy(content = content)
    }

    fun selectImage(uri: String?) {
        _createFormState.value = _createFormState.value.copy(selectedImageUri = uri)
    }

    fun createPost() {
        viewModelScope.launch {
            _createUiState.value = PostCreateUiState.Saving
            try {
                val form = _createFormState.value
                val request = PostCreateRequest(
                    title = form.title,
                    content = form.content,
                    imageUrl = form.selectedImageUri
                )
                val post = postLocalDataSource.createPost(form.author, request)
                _createUiState.value = PostCreateUiState.Success(post)
            } catch (e: Exception) {
                _createUiState.value = PostCreateUiState.Error(e.message ?: "저장 실패")
            }
        }
    }

    // ============================================================
    // 수정 화면
    // ============================================================

    private val _editUiState = MutableStateFlow<PostEditUiState>(PostEditUiState.Loading)
    val editUiState: StateFlow<PostEditUiState> = _editUiState.asStateFlow()

    private val _editFormState = MutableStateFlow(PostEditFormState())
    val editFormState: StateFlow<PostEditFormState> = _editFormState.asStateFlow()

    fun loadPostForEdit(postId: Long) {
        // 같은 게시글이면 다시 초기화 안 함 (회전 등에 대비)
        if (_editFormState.value.initializedPostId == postId) return

        viewModelScope.launch {
            _editUiState.value = PostEditUiState.Loading
            try {
                val post = postLocalDataSource.getPostDetail(postId)
                if (post == null) {
                    _editUiState.value = PostEditUiState.Error("게시글을 찾을 수 없습니다")
                } else {
                    _editUiState.value = PostEditUiState.Ready(post)
                    _editFormState.value = PostEditFormState(
                        title = post.title,
                        content = post.content,
                        originalImageUrl = post.imageUrl,
                        selectedImageUri = null,
                        initializedPostId = postId
                    )
                }
            } catch (e: Exception) {
                _editUiState.value = PostEditUiState.Error(e.message ?: "불러오기 실패")
            }
        }
    }

    fun updateEditTitle(title: String) {
        _editFormState.value = _editFormState.value.copy(title = title)
    }

    fun updateEditContent(content: String) {
        _editFormState.value = _editFormState.value.copy(content = content)
    }

    fun selectEditImage(uri: String?) {
        _editFormState.value = _editFormState.value.copy(selectedImageUri = uri)
    }

    fun updatePost(postId: Long) {
        viewModelScope.launch {
            _editUiState.value = PostEditUiState.Saving
            try {
                val form = _editFormState.value
                val finalImageUrl = form.selectedImageUri ?: form.originalImageUrl
                val request = PostCreateRequest(
                    title = form.title,
                    content = form.content,
                    imageUrl = finalImageUrl
                )
                val updated = postLocalDataSource.updatePost(postId, request)
                if (updated == null) {
                    _editUiState.value = PostEditUiState.Error("수정 실패")
                } else {
                    _editUiState.value = PostEditUiState.Success(updated)
                }
            } catch (e: Exception) {
                _editUiState.value = PostEditUiState.Error(e.message ?: "저장 실패")
            }
        }
    }

    // ============================================================
    // 삭제
    // ============================================================

    fun deletePost(postId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val deleted = postLocalDataSource.deletePost(postId)
                if (deleted) {
                    onSuccess()
                }
            } catch (e: Exception) {
                // 에러 처리 — 필요하면 별도 상태 추가
            }
        }
    }
}