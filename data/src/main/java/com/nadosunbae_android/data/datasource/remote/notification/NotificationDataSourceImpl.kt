package com.nadosunbae_android.data.datasource.remote.notification

import com.nadosunbae_android.data.api.notification.NotificationService
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationReadData
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(private val service: NotificationService) :
    NotificationDataSource {
    override suspend fun getNotification(): Response<ResponseNotificationData> {
        return service.getNotification()
    }

    override suspend fun deleteNotification(notificationId: Int): ResponseNotificationDeleteData {
        return service.deleteNotification(notificationId)
    }

    override suspend fun putReadNotification(notificationId: Int): ResponseNotificationReadData {
        return service.putReadNotification(notificationId)
    }
}