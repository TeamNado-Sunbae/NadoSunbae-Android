package com.nadosunbae_android.data.model.response.sign


import java.io.Serializable

data class ResponseSignIn(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val accesstoken: String,
        val refreshtoken: String,
        val updateAlert: UpdateAlert,
        val user: User
    ) {
        data class UpdateAlert(
            val major: String,
            val minor: String
        )
        data class User(
            val email: String,
            val firstMajorId: Int,
            val firstMajorName: String,
            val isEmailVerified: Boolean = false,
            val isReviewInappropriate: Boolean = false,
            val isReviewed: Boolean,
            val isUserReported: Boolean = false,
            val secondMajorId: Int,
            val secondMajorName: String,
            val universityId: Int,
            val userId: Int,
            val message: String? = ""
        ) : Serializable
    }
}