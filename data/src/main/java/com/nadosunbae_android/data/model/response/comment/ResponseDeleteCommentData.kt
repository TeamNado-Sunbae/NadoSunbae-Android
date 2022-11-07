package com.nadosunbae_android.data.model.response.comment

import com.nadosunbae_android.domain.model.comment.DeleteCommentData

data class ResponseDeleteCommentData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val commentId: Int,
        val isDeleted: Boolean
    )
}


fun ResponseDeleteCommentData.toEntity() : DeleteCommentData{
    return DeleteCommentData(
        success = this.success,
        commentId = this.data.commentId,
        isDeleted = this.data.isDeleted
    )
}