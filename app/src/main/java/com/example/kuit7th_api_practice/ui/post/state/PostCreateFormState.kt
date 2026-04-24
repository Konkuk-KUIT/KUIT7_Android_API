package com.example.kuit7th_api_practice.ui.post.state

// FormState???ъ슜?먭? ?낅젰 以묒씤 媛믪쓣 ?대뒗 媛앹껜?낅땲??
// ?묒꽦 ?붾㈃???띿뒪?명븘??媛믪쓣 ViewModel?먯꽌 ??踰덉뿉 愿由ы븯?ㅺ퀬 ?ъ슜?⑸땲??
data class PostCreateFormState(
    val author: String = "",
    val title: String = "",
    val content: String = "",
    val selectedImageUri: String? = null
)
