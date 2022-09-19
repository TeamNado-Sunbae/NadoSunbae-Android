package com.nadosunbae_android.domain.model.user

import java.util.*

data class UserQuestionData(
    val commentCount: Int,
    val content: String,
    val createdAt: Date,
    val postId: Int,
    val title: String,
    val isLiked: Boolean,
    val likeCount: Int,
    val id: Int,
    val nickname: String
)