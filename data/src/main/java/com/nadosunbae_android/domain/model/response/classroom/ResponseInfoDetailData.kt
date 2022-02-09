package com.nadosunbae_android.domain.model.response.classroom

import java.util.*

data class ResponseInfoDetailData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val commentCount: String,
        val commentList: List<Comment>,
        val like: Like,
        val post: Post,
        val writer: Writer
    ) {
        data class Comment(
            val commentId: Int,
            val content: String,
            val createdAt: Date?,
            val isDeleted: Boolean,
            val writer: Writer
        ) {
            data class Writer(
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

        data class Like(
            val isLiked: Boolean,
            val likeCount: String
        )

        data class Post(
            val content: String,
            val createdAt: Date?,
            val postId: Int,
            val title: String
        )

        data class Writer(
            val firstMajorName: String,
            val firstMajorStart: String,
            val nickname: String,
            val profileImageId: Int,
            val secondMajorName: String,
            val secondMajorStart: String,
            val writerId: Int
        )
    }
}