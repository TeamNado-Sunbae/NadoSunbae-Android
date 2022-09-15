package com.nadosunbae_android.domain.model.user

import java.util.*

data class UserPostData(
    val commentCount: Int,
    val content: String,
    val createdAt: Date,
    val like: Like,
    val majorName: String,
    val postId: Int,
    val title: String,
    val type: String?,
    val writer: Writer
) {
    data class Like(
        val isLiked: Boolean,
        val likeCount: Int
    )

    data class Writer(
        val id: Int,
        val nickname: String
    )
}
