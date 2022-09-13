package com.nadosunbae_android.data.model.response.user

data class ResponseUserInfo(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val bio: String?,
        val count: Int,
        val firstMajorName: String,
        val firstMajorStart: String,
        val isOnQuestion: Boolean,
        val nickname: String,
        val profileImageId: Int?,
        val responseRate: Int?,
        val secondMajorName: String,
        val secondMajorStart: String,
        val userId: Int
    )
}