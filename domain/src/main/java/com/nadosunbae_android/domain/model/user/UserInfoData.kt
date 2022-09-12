package com.nadosunbae_android.domain.model.user

data class UserInfoData(
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