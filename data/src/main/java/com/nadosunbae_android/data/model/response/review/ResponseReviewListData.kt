package com.nadosunbae_android.data.model.response.review

import java.util.*

data class ResponseReviewListData(
    val data: List<Data>,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val createdAt: Date,
        val like: Like,
        val oneLineReview: String,
        val postId: Int,
        val tagList: List<Tag>,
        val writer: Writer
    ) {
        data class Tag(
            val tagName: String
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

        data class Like(
            val isLiked: Boolean,
            val likeCount: Int
        )
    }
}