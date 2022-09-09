package com.nadosunbae_android.data.model.response.post

import com.nadosunbae_android.domain.model.post.PostWriteData

data class ResponsePostWriteData(
    val post: Post,
    val writer: Writer
) {
    data class Post(
        val answererId: Int,
        val content: String,
        val createdAt: String,
        val id: Int,
        val majorId: Int,
        val title: String,
        val type: String
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

fun ResponsePostWriteData.toEntity(): PostWriteData {
    return PostWriteData(
        answererId = post.answererId,
        content = post.content,
        createdAt = post.createdAt,
        postId = post.id,
        majorId = post.majorId,
        title = post.title,
        type = post.type,
        firstMajorName = writer.firstMajorName,
        firstMajorStart = writer.firstMajorStart,
        writeId = writer.id,
        nickname = writer.nickname,
        profileImageId = writer.profileImageId,
        secondMajorName = writer.secondMajorName,
        secondMajorStart = writer.secondMajorStart
    )

}
