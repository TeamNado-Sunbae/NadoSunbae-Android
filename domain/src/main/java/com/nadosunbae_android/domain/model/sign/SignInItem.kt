package com.nadosunbae_android.domain.model.sign

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class SignInItem(
    val status : Int,
    val success: Boolean,
    val accesstoken: String,
    val user: User
) {
    @Parcelize
    data class User(
        val email: String = "",
        val firstMajorId: Int = 0,
        val firstMajorName: String = "",
        val isReviewed: Boolean = false,
        val secondMajorId: Int = 0,
        val secondMajorName: String = "",
        val universityId: Int = 0,
        val userId: Int = 0
    ) : Parcelable
}
