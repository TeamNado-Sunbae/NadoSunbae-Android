package com.nadosunbae_android.data.model.response.mypage

import java.util.*

data class ResponseMyPageReview(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val reviewPostList: List<ReviewPost>,
        val writer: Writer
    ) {
        data class ReviewPost(
            val createdAt: Date?,
            val like: Like,
            val majorName: String,
            val oneLineReview: String,
            val postId: Int,
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