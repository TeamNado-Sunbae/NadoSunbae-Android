package com.nadosunbae_android.data.model.response.post

import com.nadosunbae_android.domain.model.post.PostUpdateData

data class ResponsePostUpdateData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val post: Post,
        val writer: Writer
    ) {
        data class Post(
            val content: String,
            val createdAt: String,
            val id: Int,
            val title: String,
            val updatedAt: String
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
}

fun ResponsePostUpdateData.toEntity(): PostUpdateData {
    return PostUpdateData(
        success = this.success,
        content = this.data.post.content,
        createdAt = this.data.post.createdAt,
        postId = this.data.post.id,
        title = this.data.post.title,
        updatedAt = this.data.post.updatedAt,
        firstMajorName = this.data.writer.firstMajorName,
        firstMajorStart = this.data.writer.firstMajorStart,
        writerId = this.data.writer.id,
        nickname = this.data.writer.nickname,
        profileImageId = this.data.writer.profileImageId,
        secondMajorName = this.data.writer.secondMajorName,
        secondMajorStart = this.data.writer.secondMajorStart
    )
}