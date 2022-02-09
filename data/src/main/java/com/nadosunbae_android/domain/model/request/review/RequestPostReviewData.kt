package com.nadosunbae_android.domain.model.request.review

data class RequestPostReviewData(
    val majorId: Int,
    val backgroundImageId: Int,
    val oneLineReview: String,
    val prosCons: String,
    val curriculum: String?,
    val recommendLecture: String?,
    val nonRecommendLecture: String?,
    val career: String?,
    val tip: String?
)
