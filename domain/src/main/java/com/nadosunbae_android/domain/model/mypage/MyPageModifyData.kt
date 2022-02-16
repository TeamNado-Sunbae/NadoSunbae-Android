package com.nadosunbae_android.domain.model.mypage

data class MyPageModifyData(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val firstMajorId: Int,
        val firstMajorStart: String,
        val isOnQuestion: Boolean,
        val nickname: String,
        val secondMajorId: Int,
        val secondMajorStart: String,
        val updatedAt: String
    )
}