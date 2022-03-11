package com.nadosunbae_android.domain.model.sign

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class SignInData(
    val status : Int,
    val success: Boolean,
    val accessToken: String,
    val refreshToken: String,
    val user: User
) {
    @Parcelize
    data class User(
        val email: String = "",
        val firstMajorId: Int = 0,
        var firstMajorName: String = "",
        var isReviewed: Boolean = false,
        val secondMajorId: Int = 0,
        val secondMajorName: String = "",
        val universityId: Int = 0,
        val userId: Int = 0,
        val isEmailVerified: Boolean = false,
        val isUserReported : Boolean = false,
        var isReviewInappropriate : Boolean = false,
        val message : String? = ""
    ) : Parcelable
}
