package com.example.kuit7th_api_practice.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object PostListRoute

@Serializable
data class PostDetailRoute(val postId: Long)

@Serializable
object PostCreateRoute

@Serializable
data class PostEditRoute(val postId: Long)
