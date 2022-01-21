package com.nadosunbae_android.data.model.response.notification

data class ResponseNotificationReadData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val createdAt: String,
        val isDeleted: Boolean,
        val isRead: Boolean,
        val notificationId: Int,
        val receiverId: Int,
        val updatedAt: String
    )
}