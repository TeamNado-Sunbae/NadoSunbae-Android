package com.nadosunbae_android.domain.model.user

import java.util.*

data class UserPostData(
    val commentCount: Int,
    val content: String,
    val createdAt: Date,
    val isLiked: Boolean,
    val likeCount: Int,
    val majorName: String,
    val postId: Int,
    val title: String,
    val type: String?,
    val writerId: Int,
    val nickname: String
)