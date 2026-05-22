package com.example.kuit7th_api_practice.data.dto

import com.example.kuit7th_api_practice.domain.repository.model.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val userId: Int,
    val id: Int?=null,//posteditscreen에서 응답객체에 id가 없어서 문제 발생
    val title: String,
    val body: String
)

fun PostDto.toDomain(fallbackId: Int? = null): Post = Post(
    id = id ?: fallbackId ?: error("Post id is missing"),//id없으면 fallbackid, 없으면 error
    userId = userId,
    title = title,
    body = body
)