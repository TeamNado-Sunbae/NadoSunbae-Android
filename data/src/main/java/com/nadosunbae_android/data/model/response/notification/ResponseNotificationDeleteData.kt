package com.nadosunbae_android.data.model.response.notification

import com.nadosunbae_android.domain.model.notification.NotificationDeleteData

data class ResponseNotificationDeleteData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val isDeleted: Boolean,
        val notificationId: Int
    )
}


fun ResponseNotificationDeleteData.toEntity() : NotificationDeleteData{
    return NotificationDeleteData(
        isDeleted = this.data.isDeleted,
        notificationId = this.data.notificationId
    )
}