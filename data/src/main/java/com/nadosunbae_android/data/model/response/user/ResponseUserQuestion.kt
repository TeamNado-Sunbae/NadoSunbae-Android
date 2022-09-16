package com.nadosunbae_android.data.model.response.user

import java.util.*

data class ResponseUserQuestion(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val postList: List<Post>
    ) {
        data class Post(
            val commentCount: Int,
            val content: String,
            val createdAt: Date,
            val like: Like,
            val postId: Int,
            val title: String,
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
    }
}