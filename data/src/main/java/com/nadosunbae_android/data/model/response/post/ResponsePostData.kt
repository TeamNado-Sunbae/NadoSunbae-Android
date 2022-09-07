package com.nadosunbae_android.data.model.response.post

import com.nadosunbae_android.domain.model.post.PostData
import java.util.*

data class ResponsePostData(
    val commentCount: Int,
    val content: String,
    val createdAt: Date?,
    val like: Like,
    val majorName: String,
    val postId: Int,
    val title: String,
    val type: String,
    val writer: Writer
) {
    data class Like(
        val isLiked: Boolean,
        val likeCount: Int
    )

    data class Writer(
        val id: Int,
        val nickname: String
    )
}

fun ResponsePostData.toEntity(): PostData {
    return PostData(
        commentCount = this.commentCount,
        content = this.content,
        createdAt = this.createdAt,
        majorName = this.majorName,
        postId = this.postId,
        title = this.title,
        type = this.type,
        isLiked = this.like.isLiked,
        likeCount = this.like.likeCount,
        id = this.writer.id,
        nickname = this.writer.nickname
    )
}