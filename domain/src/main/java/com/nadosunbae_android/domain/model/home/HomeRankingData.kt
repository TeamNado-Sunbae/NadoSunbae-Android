package com.nadosunbae_android.domain.model.home

data class HomeRankingData(
    val firstMajorName: String,
    val firstMajorStart: String,
    val id: Int,
    val nickname: String,
    val profileImageId: Int,
    val rate: Int?,
    val secondMajorName: String,
    val secondMajorStart: String
)