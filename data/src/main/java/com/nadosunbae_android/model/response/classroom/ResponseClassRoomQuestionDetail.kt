package com.nadosunbae_android.model.response.classroom

import java.util.*

data class  ResponseClassRoomQuestionDetail(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val answererId: Int,
        val like: Like,
        val messageList: List<Message>,
        val questionerId: Int
    ) {
        data class Like(
            val isLiked: Boolean,
            val likeCount: String
        )

        data class Message(
            val content: String,
            val createdAt: Date?,
            val isDeleted: Boolean,
            val messageId: Int,
            val title: String,
            val writer: Writer
        ) {
            data class Writer(
                val firstMajorName: String,
                val firstMajorStart: String,
                val isQuestioner: Boolean,
                val nickname: String,
                val profileImageId: Int,
                val secondMajorName: String,
                val secondMajorStart: String,
                val writerId: Int
            )
        }
    }
}