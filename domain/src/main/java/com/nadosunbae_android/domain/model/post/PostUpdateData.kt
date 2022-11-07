package com.nadosunbae_android.domain.model.post

data class PostUpdateData(
    val success: Boolean,
    val content: String,
    val createdAt: String,
    val postId: Int,
    val title: String,
    val updatedAt: String,
    val firstMajorName: String,
    val firstMajorStart: String,
    val writerId: Int,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String
)

data class PostUpdateParam(
    val title : String,
    val content : String?
)
