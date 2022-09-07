package com.nadosunbae_android.data.model.response.post

import com.nadosunbae_android.domain.model.post.PostDetailData

data class ResponsePostDetailData(
    val commentCount: Int,
    val commentList: List<Comment>,
    val like: Like,
    val post: Post,
    val writer: Writer
) {
    data class Comment(
        val content: String,
        val createdAt: String,
        val id: Int,
        val isDeleted: Boolean,
        val writer: Writer
    ) {
        data class Writer(
            val firstMajorName: String,
            val firstMajorStart: String,
            val id: Int,
            val isPostWriter: Boolean,
            val nickname: String,
            val profileImageId: Int,
            val secondMajorName: String,
            val secondMajorStart: String
        )
    }

    data class Like(
        val isLiked: Boolean,
        val likeCount: Int
    )

    data class Post(
        val content: String,
        val createdAt: String,
        val id: Int,
        val majorName: String,
        val title: String
    )

    data class Writer(
        val firstMajorName: String,
        val firstMajorStart: String,
        val id: Int,
        val nickname: String,
        val profileImageId: Int,
        val secondMajorName: String,
        val secondMajorStart: String
    )
}

fun ResponsePostDetailData.toEntity(): PostDetailData {
    return PostDetailData(
        commentCount = this.commentCount,
        commentList = this.commentList.map {
            PostDetailData.Comment(
                content = it.content,
                createdAt = it.createdAt,
                commentId = it.id,
                isDeleted = it.isDeleted,
                firstMajorName = it.writer.firstMajorName,
                firstMajorStart = it.writer.firstMajorStart,
                commentWriterId = it.writer.id,
                isPostWriter = it.writer.isPostWriter,
                nickname = it.writer.nickname,
                profileImageId = it.writer.profileImageId,
                secondMajorName = it.writer.secondMajorName,
                secondMajorStart = it.writer.secondMajorStart
            )
        },
        isLiked = this.like.isLiked,
        likeCount = this.like.likeCount,
        content = this.post.content,
        createdAt = this.post.createdAt,
        postId = this.post.id,
        majorName = this.post.majorName,
        title = this.post.title,
        firstMajorName = this.writer.firstMajorName,
        firstMajorStart = this.writer.firstMajorStart,
        writerId = this.writer.id,
        nickname = this.writer.nickname,
        profileImageId = this.writer.profileImageId,
        secondMajorName = this.writer.secondMajorName,
        secondMajorStart = this.writer.secondMajorStart
    )
}