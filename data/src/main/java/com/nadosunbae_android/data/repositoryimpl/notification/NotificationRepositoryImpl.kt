package com.nadosunbae_android.data.repositoryimpl.notification

import com.nadosunbae_android.data.datasource.remote.notification.NotificationDataSource
import com.nadosunbae_android.data.mapper.notification.NotificationMapper
import com.nadosunbae_android.domain.model.notification.NotificationDeleteData
import com.nadosunbae_android.domain.model.notification.NotificationListData
import com.nadosunbae_android.domain.model.notification.NotificationReadData
import com.nadosunbae_android.domain.repository.notification.NotificationRepository

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