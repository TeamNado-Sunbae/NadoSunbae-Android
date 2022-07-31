package com.nadosunbae_android.domain.model.home

data class HomeCommunityData(
    val option : String,
    val title : String,
    val content: String,
    val who : String,
    val date : String,
    val likeCount : Int,
    val commentCount : Int
)
