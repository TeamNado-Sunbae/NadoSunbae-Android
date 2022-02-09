package com.nadosunbae_android.domain.model.classroom

import java.util.*

data class QuestionDetailData(
    val answererId: Int,
    val isLiked : Boolean,
    val likeCount : String,
    val messageList: List<Message>,
    val questionerId: Int,
) {
    data class Message(
        val content: String,
        val createdAt: Date?,
        val isDeleted: Boolean,
        val messageId: Int,
        val title: String,
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

