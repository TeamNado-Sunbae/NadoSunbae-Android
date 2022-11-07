package com.nadosunbae_android.data.model.response.notification

import com.nadosunbae_android.domain.model.notification.NotificationData
import java.util.*

data class ResponseNotificationData(
    val notificationList: List<Notification>
) {
    data class Notification(
        val commentId: Int,
        val content: String,
        val createdAt: Date?,
        val isRead: Boolean,
        val notificationId: Int,
        val notificationTypeId: Int,
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

fun ResponseNotificationData.toEntity(): List<NotificationData> {
    return this.notificationList.map {
        NotificationData(
            commentId = it.commentId,
            content = it.content,
            createdAt = it.createdAt,
            isRead = it.isRead,
            notificationId = it.notificationId,
            notificationTypeId = it.notificationTypeId,
            postId = it.postId,
            senderNickname = it.sender.nickname,
            senderId = it.sender.senderId,
            profileImageId = it.sender.profileImageId
        )
    }
}