package com.nadosunbae_android.data.model.response.mypage

data class ResponseMyPageModify(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val bio: String,
        val firstMajorId: Int,
        val firstMajorStart: String,
        val isOnQuestion: Boolean,
        val nickname: String,
        val nicknameUpdatedAt: String,
        val profileImageId: Int,
        val secondMajorId: Int,
        val secondMajorStart: String,
        val updatedAt: String
    )
}