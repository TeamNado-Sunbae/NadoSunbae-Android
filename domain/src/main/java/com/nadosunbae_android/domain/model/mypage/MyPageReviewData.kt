package com.nadosunbae_android.domain.model.mypage

import java.util.*

data class MyPageReviewData(
    val data: Data,
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