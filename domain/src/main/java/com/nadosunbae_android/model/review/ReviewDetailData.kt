package com.nadosunbae_android.model.review

import java.io.Serializable
import java.util.*

data class ReviewDetailData(
    val backgroundImage: BackgroundImage,
    val like: Like,
    val post: Post,
    val writer: Writer
) : Serializable {
    data class BackgroundImage(
        val imageId: Int,
        val imageUrl: String
    ) : Serializable

    data class Like(
        var isLiked: Boolean,
        val likeCount: String
    ) : Serializable

    data class Post(
        val contentList: List<Content>,
        val createdAt: Date,
        val oneLineReview: String,
        val postId: Int
    ) : Serializable {
        data class Content(
            val content: String,
            val title: String
        ) : Serializable
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
    ) : Serializable
}
