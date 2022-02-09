package com.nadosunbae_android.data.model.mypage

data class MyPageMyInfo(
    val data: Data,
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


