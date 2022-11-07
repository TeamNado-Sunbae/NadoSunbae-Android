package com.nadosunbae_android.data.model.response.user

import java.util.*

data class ResponseUserReview(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val reviewList: List<Review>,
        val writer: Writer
    ) {
        data class Review(
            val createdAt: Date,
            val id: Int,
            val like: Like,
            val majorName: String,
            val oneLineReview: String,
            val tagList: List<Tag>
        ) {
            data class Like(
                val isLiked: Boolean,
                val likeCount: Int
            )

            data class Tag(
                val tagName: String
            )
        }

        data class Writer(
            val nickname: String,
            val writerId: Int
        )
    }
}