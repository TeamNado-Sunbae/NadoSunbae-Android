package com.nadosunbae_android.domain.model.community

data class CommunityMainData(
    val commentCount: Int,
    val content: String,
    val createdAt: String,
    val majorName: String,
    val postId: Int,
    val title: String,
    val type: String,
    val isLiked: Boolean,
    val likeCount: Int,
    val id: Int,
    val nickname: String
)
