package com.nadosunbae_android.repositoryimpl.notification

import com.nadosunbae_android.datasource.remote.notification.NotificationDataSource
import com.nadosunbae_android.datasource.remote.notification.NotificationDataSourceImpl
import com.nadosunbae_android.mapper.notification.NotificationMapper
import com.nadosunbae_android.model.notification.NotificationDeleteData
import com.nadosunbae_android.model.notification.NotificationListData
import com.nadosunbae_android.model.notification.NotificationReadData
import com.nadosunbae_android.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.model.response.notification.ResponseNotificationReadData
import com.nadosunbae_android.repository.notification.NotificationRepository
import retrofit2.Response

class NotificationRepositoryImpl(private val dataSource : NotificationDataSource) : NotificationRepository {
    override suspend fun getNotification(receiverId: Int): List<NotificationListData> {
        return NotificationMapper.mapperToNotificationListData(dataSource.getNotification(receiverId))
    }

    override suspend fun deleteNotification(notificationId: Int): NotificationDeleteData {
        return NotificationMapper.mapperToNotificationDeleteData(dataSource.deleteNotification(notificationId))
    }

    override suspend fun putReadNotification(notificationId: Int): NotificationReadData {
        return NotificationMapper.mapperToNotificationReadData(dataSource.putReadNotification(notificationId))
    }
}