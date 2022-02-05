package com.nadosunbae_android.model.review

import java.util.*

data class ReviewWriteData(
    val success: String,
    val postId: Int,
    val createdAt: Date,
    val oneLineReview: String,
    val contentList: List<Content>,
    val backgroundImageId: Int,
    val backgroundImageUrl: String,
    val isLiked: Boolean,
    val likeCount: Int,
    val writerId: Int
) {
    data class Content(
        val title: String,
        val content: String
    )
}
