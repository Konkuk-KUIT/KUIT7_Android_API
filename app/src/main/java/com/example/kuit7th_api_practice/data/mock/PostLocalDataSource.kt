package com.example.kuit7th_api_practice.data.mock

import com.example.kuit7th_api_practice.data.model.request.PostCreateRequest
import com.example.kuit7th_api_practice.data.model.response.PostResponse

interface PostLocalDataSource {

    suspend fun getPosts(): List<PostResponse>
    suspend fun getPostDetail(postId: Long): PostResponse?
    suspend fun createPost(authorName: String, request: PostCreateRequest): PostResponse
    suspend fun updatePost(postId: Long, request: PostCreateRequest): PostResponse?
    suspend fun deletePost(postId: Long): Boolean
}
