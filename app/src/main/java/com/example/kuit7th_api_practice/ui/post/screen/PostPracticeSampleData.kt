package com.example.kuit7th_api_practice.ui.post.screen

import com.example.kuit7th_api_practice.data.model.response.AuthorResponse
import com.example.kuit7th_api_practice.data.model.response.PostResponse

object PostPracticeSampleData {
    val posts = listOf(
        PostResponse(
            id = 1L,
            title = "첫 번째 실습용 게시글",
            content = "안녕하세요! 5주차 실습을 하러 오신걸 환영합니다.",
            imageUrl = null,
            author = AuthorResponse(
                id = 1L,
                username = "anonymous",
                profileImageUrl = null
            ),
            createdAt = "2026-04-25T09:30:00",
            updatedAt = "2026-04-25T09:30:00"
        ),
        PostResponse(
            id = 2L,
            title = "두 번째 실습용 게시글",
            content = "실습에서는 이 화면을 ViewModel 상태와 연결하는 작업을 진행하게 됩니다.",
            imageUrl = null,
            author = AuthorResponse(
                id = 2L,
                username = "kuit_member",
                profileImageUrl = null
            ),
            createdAt = "2026-04-25T10:15:00",
            updatedAt = "2026-04-25T10:15:00"
        )
    )

    fun findPost(postId: Long): PostResponse {
        return posts.firstOrNull { it.id == postId } ?: posts.first()
    }
}
