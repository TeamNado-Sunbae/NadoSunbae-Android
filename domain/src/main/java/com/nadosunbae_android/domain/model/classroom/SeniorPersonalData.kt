package com.nadosunbae_android.domain.model.classroom

data class SeniorPersonalData(
    val firstMajorName: String,
    val firstMajorStart: String,
    val isOnQuestion: Boolean,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String,
    val userId: Int
)
