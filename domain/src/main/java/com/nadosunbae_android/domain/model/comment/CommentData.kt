package com.nadosunbae_android.domain.model.comment

data class CommentData(
    val commentId: Int,
    val content: String,
    val createdAt: String,
    val isDeleted: Boolean,
    val postId: Int,
    val firstMajorName: String,
    val firstMajorStart: String,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String,
    val writerId: Int
)

data class CommentParam(
    val postId : String,
    val content : String
)