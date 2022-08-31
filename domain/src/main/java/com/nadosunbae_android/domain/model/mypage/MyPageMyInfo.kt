package com.nadosunbae_android.domain.model.mypage

data class MyPageMyInfo(
    val data: Data,
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
        val responseRate: Any?,
        val secondMajorName: String,
        val secondMajorStart: String,
        val userId: Int
    )
}


