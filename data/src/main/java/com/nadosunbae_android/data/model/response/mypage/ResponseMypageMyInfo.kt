package com.nadosunbae_android.data.model.response.mypage

data class ResponseMypageMyInfo(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val count: Int,
        val firstMajorName: String,
        val firstMajorStart: String,
        val isOnQuestion: Boolean,
        val nickname: String,
        val profileImageId: Int,
        val secondMajorName: String,
        val secondMajorStart: String,
        val userId: Int
    )
}