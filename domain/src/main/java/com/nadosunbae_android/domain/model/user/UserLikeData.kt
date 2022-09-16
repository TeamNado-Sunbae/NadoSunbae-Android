package com.nadosunbae_android.domain.model.user

import java.util.*

data class UserLikeData(
    val commentCount: Int?,
    val content: String?,
    val createdAt: Date?,
    val id: Int?,
    val majorName: String?,
    val title: String?,
    val type: String?,
    val isLiked: Boolean?,
    val likeCount: Int?,
    val writerId: Int?,
    val nickname: String?,
    val tagName: List<String>?
)