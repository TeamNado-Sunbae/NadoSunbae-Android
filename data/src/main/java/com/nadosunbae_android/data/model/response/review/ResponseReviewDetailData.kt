package com.nadosunbae_android.data.model.response.review

import java.util.*

data class ResponseReviewDetailData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val backgroundImage: BackgroundImage,
        val like: Like,
        val post: Post,
        val writer: Writer
    ) {
        data class BackgroundImage(
            val imageId: Int
        )

        data class Like(
            var isLiked: Boolean,
            val likeCount: Int
        )

        data class Post(
            val contentList: List<Content>,
            val createdAt: Date,
            val oneLineReview: String,
            val postId: Int
        ) {
            data class Content(
                val content: String,
                val title: String
            )
        }

        data class Writer(
            val firstMajorName: String,
            val firstMajorStart: String,
            val isOnQuestion: Boolean,
            val isReviewed: Boolean,
            val nickname: String,
            val profileImageId: Int,
            val secondMajorName: String,
            val secondMajorStart: String,
            val writerId: Int
        )
    }
}