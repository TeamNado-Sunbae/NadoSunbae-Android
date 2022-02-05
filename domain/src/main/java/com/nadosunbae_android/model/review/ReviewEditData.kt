package com.nadosunbae_android.model.review

data class ReviewEditData(
    val backgroundImageId: Int,
    val oneLineReview: String,
    val prosCons: String,
    val curriculum: String,
    val career: String,
    val recommendLecture: String,
    val nonRecommendLecture: String,
    val tip: String
)
