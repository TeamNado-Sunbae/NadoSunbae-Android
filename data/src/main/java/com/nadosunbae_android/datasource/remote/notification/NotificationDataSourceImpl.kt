package com.nadosunbae_android.datasource.remote.notification

import com.nadosunbae_android.api.notification.NotificationService
import com.nadosunbae_android.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.model.response.notification.ResponseNotificationReadData

class NotificationDataSourceImpl(private val service : NotificationService) : NotificationDataSource {
    override suspend fun getNotification(receiverId: Int): ResponseNotificationListData {
        return service.getNotification(receiverId)
    }

    override suspend fun deleteNotification(notificationId: Int): ResponseNotificationDeleteData {
        return service.deleteNotification(notificationId)
    }

    override suspend fun putReadNotification(notificationId: Int): ResponseNotificationReadData {
        return service.putReadNotification(notificationId)
    }
}