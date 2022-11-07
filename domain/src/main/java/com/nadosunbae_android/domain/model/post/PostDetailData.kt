package com.nadosunbae_android.domain.model.post

import java.util.*

data class PostDetailData(
    val commentCount: Int,
    val commentList: List<Comment>,
    val answererId: Int,
    val isLiked: Boolean,
    val type: String?,
    val likeCount: Int,
    val content: String,
    val createdAt: Date?,
    val postId: Int,
    val majorName: String,
    val title: String,
    val firstMajorName: String,
    val firstMajorStart: String,
    val writerId: Int,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String
) {
    data class Comment(
        var content: String,
        val createdAt: Date?,
        val commentId: Int,
        var isDeleted: Boolean,
        val firstMajorName: String,
        val firstMajorStart: String,
        val commentWriterId: Int,
        val isPostWriter: Boolean,
        val nickname: String,
        val profileImageId: Int,
        val secondMajorName: String,
        val secondMajorStart: String
    )

    companion object {
        val DEFAULT = PostDetailData(
            0,
            emptyList(),
            0,
            false,
            "",
            0,
            "",
            null,
            0,
            "",
            "",
            "",
            "",
            0,
            "",
            0,
            "",
            ""
        )
    }
}





