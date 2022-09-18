package com.nadosunbae_android.data.model.response.comment

import com.nadosunbae_android.domain.model.comment.CommentData

data class ResponseCommentData(
    val commentId: Int,
    val content: String,
    val createdAt: String,
    val isDeleted: Boolean,
    val postId: Int,
    val writer: Writer
) {
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

fun ResponseCommentData.toEntity(): CommentData {
    return CommentData(
        commentId = this.commentId,
        content = this.content,
        createdAt = this.createdAt,
        isDeleted = this.isDeleted,
        postId = this.postId,
        firstMajorName = this.writer.firstMajorName,
        firstMajorStart = this.writer.firstMajorStart,
        nickname = this.writer.nickname,
        profileImageId = this.writer.profileImageId,
        secondMajorName = this.writer.secondMajorName,
        secondMajorStart = this.writer.secondMajorStart,
        writerId = this.writer.writerId
    )

}