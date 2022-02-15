package com.nadosunbae_android.domain.model.review

data class ReviewDeleteData(
    val isDeleted: Boolean,
    val isReviewed: Boolean,
    val postId: String
)
