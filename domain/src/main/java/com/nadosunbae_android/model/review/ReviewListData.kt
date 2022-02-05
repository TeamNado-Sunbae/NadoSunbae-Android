package com.nadosunbae_android.model.review

import java.util.*

data class ReviewListData(
    val reviews: List<Review>
) {
    data class Review(
        val createdAt: Date,
        val likeCount: String,
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
    }
}
