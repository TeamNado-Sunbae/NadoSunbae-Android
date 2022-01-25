package com.nadosunbae_android.repositoryimpl.notification

import com.nadosunbae_android.datasource.remote.notification.NotificationDataSource
import com.nadosunbae_android.datasource.remote.notification.NotificationDataSourceImpl
import com.nadosunbae_android.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.model.response.notification.ResponseNotificationReadData
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

    override fun deleteNotification(
        notificationId: Int,
        onResponse: (Response<ResponseNotificationDeleteData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return notificationDataSource.deleteNotification(notificationId, onResponse, onFailure)
    }

    override fun putReadNotification(
        notificationId: Int,
        onResponse: (Response<ResponseNotificationReadData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return notificationDataSource.putReadNotification(notificationId, onResponse, onFailure)
    }
}