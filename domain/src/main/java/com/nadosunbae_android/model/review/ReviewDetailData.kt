package com.nadosunbae_android.model.review

import java.io.Serializable
import java.util.*

data class ReviewDetailData(
    val backgroundImageId: Int,
    val backgroundImageUrl: String,
    val isLiked: Boolean,
    val likeCount: String,
    val contentList: List<Content>,
    val createdAt: Date,
    val oneLineReview: String,
    val postId: Int,
    val firstMajorName: String,
    val firstMajorStart: String,
    val isOnQuestion: Boolean,
    val isReviewed: Boolean,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String,
    val writerId: Int
) : Serializable {

    data class Content(
        val title: String,
        val content: String
    ) : Serializable
}
