package com.nadosunbae_android.domain.model.classroom

import java.util.*

data class InfoDetailData(
    val commentCount: String,
    val commentList: List<Comment>,
    val isLiked: Boolean,
    val likeCount: String,
    val content: String,
    val createdAt: Date?,
    val postId: Int,
    val title: String,
    val firstMajorName: String,
    val firstMajorStart: String,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String,
    val writerId: Int
) {
    data class Comment(
        val commentId: Int,
        val content: String,
        val createdAt: Date?,
        val isDeleted: Boolean,
        val firstMajorName: String,
        val firstMajorStart: String,
        val isPostWriter: Boolean,
        val nickname: String,
        val profileImageId: Int,
        val secondMajorName: String,
        val secondMajorStart: String,
        val writerId: Int
    )
}

