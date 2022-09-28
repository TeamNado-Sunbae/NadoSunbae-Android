package com.nadosunbae_android.data.model.response.notification

import com.nadosunbae_android.domain.model.notification.NotificationReadData

data class ResponseNotificationReadData(
    val data: List<Data>,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val id: Int,
        val isRead: Boolean
    )
}

fun ResponseNotificationReadData.toEntity() : List<NotificationReadData>{
    return this.data.map {
        NotificationReadData(
            id = it.id,
            isRead = it.isRead
        )
    }
}