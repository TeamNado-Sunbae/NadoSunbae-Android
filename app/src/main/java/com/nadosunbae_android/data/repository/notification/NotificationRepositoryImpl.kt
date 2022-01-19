package com.nadosunbae_android.data.repository.notification

import com.nadosunbae_android.data.datasource.remote.notification.NotificationDataSource
import com.nadosunbae_android.data.datasource.remote.notification.NotificationDataSourceImpl
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationListData
import retrofit2.Response

class NotificationRepositoryImpl : NotificationRepository {
    private val notificationDataSource : NotificationDataSource = NotificationDataSourceImpl()

    override fun getNotification(
        receiverId: Int,
        onResponse: (Response<ResponseNotificationListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return notificationDataSource.getNotification(receiverId, onResponse, onFailure)
    }
}