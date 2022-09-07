package com.nadosunbae_android.domain.model.post

data class PostDetailData(
    val commentCount: Int,
    val commentList: List<Comment>,
    val isLiked: Boolean,
    val likeCount: Int,
    val content: String,
    val createdAt: String,
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
        val content: String,
        val createdAt: String,
        val commentId: Int,
        val isDeleted: Boolean,
        val firstMajorName: String,
        val firstMajorStart: String,
        val commentWriterId: Int,
        val isPostWriter: Boolean,
        val nickname: String,
        val profileImageId: Int,
        val secondMajorName: String,
        val secondMajorStart: String
    )
}