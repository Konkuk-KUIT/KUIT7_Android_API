package com.example.kuit7th_api_practice.ui.post.state

data class PostEditFormState(
    val title: String = "",
    val content: String = "",
    val originalImageUrl: String? = null,
    val selectedImageUri: String? = null,
    val initializedPostId: Long? = null
)
