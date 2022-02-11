package com.nadosunbae_android.domain.model.sign

import java.io.Serializable

data class SignInItem(
    val status : Int,
    val success: Boolean,
    val accesstoken: String,
    val user: User
) {
    data class User(
        val email: String,
        val firstMajorId: Int,
        val firstMajorName: String,
        val isReviewed: Boolean,
        val secondMajorId: Int,
        val secondMajorName: String,
        val universityId: Int,
        val userId: Int
    ) : Serializable
}
