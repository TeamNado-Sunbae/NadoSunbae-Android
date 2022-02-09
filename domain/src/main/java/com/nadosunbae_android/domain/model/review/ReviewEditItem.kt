package com.nadosunbae_android.domain.model.review

data class ReviewEditItem(
    val backgroundImageId: Int,
    val oneLineReview: String,
    val prosCons: String,
    val curriculum: String,
    val career: String,
    val recommendLecture: String,
    val nonRecommendLecture: String,
    val tip: String
)
