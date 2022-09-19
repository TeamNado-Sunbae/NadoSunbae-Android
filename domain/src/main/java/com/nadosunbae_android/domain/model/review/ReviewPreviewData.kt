package com.nadosunbae_android.domain.model.review

import java.util.*

data class ReviewPreviewData(
    val createdAt: Date,
    val likeCount: Int,
    val isLiked: Boolean,
    val oneLineReview: String,
    val id: Int,
    val tagList: List<String>,
    val firstMajorName: String,
    val firstMajorStart: String,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String,
    val writerId: Int
)
