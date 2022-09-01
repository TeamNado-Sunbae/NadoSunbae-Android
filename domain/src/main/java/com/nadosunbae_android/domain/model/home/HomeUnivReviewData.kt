package com.nadosunbae_android.domain.model.home

import java.util.*

data class HomeUnivReviewData(
    val createdAt: Date,
    val id: Int,
    val majorName: String,
    val oneLineReview: String,
    val tagList: List<String>,
    val isLiked: Boolean,
    val likeCount: Int,
)


