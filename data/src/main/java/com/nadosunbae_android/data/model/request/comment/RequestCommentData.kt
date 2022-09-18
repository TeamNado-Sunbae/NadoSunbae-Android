package com.nadosunbae_android.data.model.request.comment

import com.nadosunbae_android.domain.model.comment.CommentParam

data class RequestCommentData(
    val postId: String,
    val content: String
)


fun CommentParam.toEntity(): RequestCommentData {
    return RequestCommentData(
        postId = this.postId,
        content = this.content
    )
}