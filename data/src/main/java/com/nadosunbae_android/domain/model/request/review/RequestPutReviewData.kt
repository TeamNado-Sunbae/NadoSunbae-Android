package com.nadosunbae_android.domain.model.request.review

data class RequestPutReviewData(
    val backgroundImageId: Int,
    val oneLineReview: String,
    val prosCons: String,
    val curriculum: String,
    val career: String,
    val recommendLecture: String,
    val nonRecommendLecture: String,
    val tip: String
)
