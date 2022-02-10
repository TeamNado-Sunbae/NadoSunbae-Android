package com.nadosunbae_android.model.response.mypage

data class ResponseMypageMyInfo(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val firstMajorName: String,
        val firstMajorStart: String,
        val isOnQuestion: Boolean,
        val likeCount: String,
        val nickname: String,
        val profileImageId: Int,
        val secondMajorName: String,
        val secondMajorStart: String,
        val userId: Int
    )
}