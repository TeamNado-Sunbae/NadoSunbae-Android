package com.nadosunbae_android.model.notification


//알림 읽은 경우
data class NotificationReadData(
    val createdAt: String,
    val isDeleted: Boolean,
    val isRead: Boolean,
    val notificationId: Int,
    val receiverId: Int,
    val updatedAt: String
)
