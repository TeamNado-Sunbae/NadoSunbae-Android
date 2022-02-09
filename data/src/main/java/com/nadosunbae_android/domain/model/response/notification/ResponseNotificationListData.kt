package com.nadosunbae_android.domain.model.response.notification

import java.util.*

data class ResponseNotificationListData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val notificationList: List<Notification>
    ) {
        data class Notification(
            val content: String,
            val createdAt: Date,
            val isDeleted: Boolean,
            val isRead: Boolean,
            val isQuestionToPerson : Boolean,
            val notificationId: Int,
            val notificationType: Int,
            val postId: Int,
            val sender: Sender
        ) {
            data class Sender(
                val nickname: String,
                val profileImageId: Int,
                val senderId: Int
            )
        }
    }
}