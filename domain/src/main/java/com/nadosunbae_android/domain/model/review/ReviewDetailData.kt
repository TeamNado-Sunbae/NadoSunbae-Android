package com.nadosunbae_android.domain.model.review

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class ReviewDetailData(
    val backgroundImageId: Int,
    val isLiked: Boolean,
    val likeCount: Int,
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
) : Parcelable {

    @Parcelize
    data class Content(
        val title: String,
        val content: String
    ) : Parcelable
}
