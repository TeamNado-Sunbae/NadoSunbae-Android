package com.nadosunbae_android.domain.model.classroom

data class CommentUpdateData(
    val commentId: Int,
    val content: String,
    val createdAt: String,
    val isDeleted: Boolean,
    val postId: Int,
    val updatedAt: String,
    val firstMajorName: String,
    val firstMajorStart: String,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String,
    val writerId: Int
)
