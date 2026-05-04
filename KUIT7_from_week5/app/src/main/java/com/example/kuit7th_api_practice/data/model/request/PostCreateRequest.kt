package com.example.kuit7th_api_practice.data.model.request

data class PostCreateRequest(
    val title: String,
    val content: String,
    val imageUrl: String?
)
