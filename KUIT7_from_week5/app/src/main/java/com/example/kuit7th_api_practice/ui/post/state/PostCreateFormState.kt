package com.example.kuit7th_api_practice.ui.post.state

data class PostCreateFormState(
    val author: String = "",
    val title: String = "",
    val content: String = "",
    val selectedImageUri: String? = null
)
