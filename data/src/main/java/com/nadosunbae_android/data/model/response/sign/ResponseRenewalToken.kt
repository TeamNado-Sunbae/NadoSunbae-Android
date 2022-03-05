package com.nadosunbae_android.data.model.response.sign

data class ResponseRenewalToken(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val accesstoken: String,
        val refreshtoken: String,
        val user: User
    ) {
        data class User(
            val email: String,
            val firstMajorId: Int,
            val firstMajorName: String,
            val isEmailVerified: Boolean,
            val isReviewInappropriate: Boolean,
            val isReviewed: Boolean,
            val isUserReported: Boolean,
            val secondMajorId: Int,
            val secondMajorName: String,
            val universityId: Int,
            val userId: Int
        )
    }
}