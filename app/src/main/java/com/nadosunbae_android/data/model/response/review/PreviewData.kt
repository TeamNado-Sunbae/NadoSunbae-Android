package com.nadosunbae_android.data.model.response.review

data class PreviewData (
    val createAt: String,
    val likes: Int,
    val oneLineReview: String,
    val contentList: List<String>,
    val nickname: String,
    val firstMajor: String,
    val secondMajor: String
)
