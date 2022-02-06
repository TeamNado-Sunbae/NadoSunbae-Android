package com.nadosunbae_android.model.sign

//로그인
data class SignInData(
    val success: Boolean,
    val accesstoken: String,
    val user: List<User>
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
    )
}
