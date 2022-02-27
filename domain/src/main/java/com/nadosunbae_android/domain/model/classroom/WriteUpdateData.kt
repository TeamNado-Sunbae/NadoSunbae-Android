package com.nadosunbae_android.domain.model.classroom

data class WriteUpdateData(
    val success : Boolean,
    val isLiked: Boolean,
    val likeCount: Int,
    val content: String,
    val createdAt: String,
    val postId: Int,
    val title: String,
    val updatedAt: String,
    val firstMajorName: String,
    val firstMajorStart: String,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String,
    val writerId: Int
)
