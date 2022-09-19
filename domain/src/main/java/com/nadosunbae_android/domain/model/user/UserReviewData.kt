package com.nadosunbae_android.domain.model.user

import java.util.*

data class UserReviewData(
    val reviewList: List<Review>
) {
    data class Review(
        val nickname: String,
        val writerId: Int,
        val createdAt: Date,
        val id: Int,
        val majorName: String,
        val oneLineReview: String,
        val tagName: List<String>,
        val isLiked: Boolean,
        val likeCount: Int
    )
}


