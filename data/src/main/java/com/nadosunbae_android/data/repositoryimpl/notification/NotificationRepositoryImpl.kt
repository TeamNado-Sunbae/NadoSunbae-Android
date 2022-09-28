package com.nadosunbae_android.data.repositoryimpl.notification

import com.nadosunbae_android.data.datasource.remote.notification.NotificationDataSource
import com.nadosunbae_android.data.model.response.notification.toEntity
import com.nadosunbae_android.domain.model.notification.NotificationData
import com.nadosunbae_android.domain.model.notification.NotificationDeleteData
import com.nadosunbae_android.domain.model.notification.NotificationReadData
import com.nadosunbae_android.domain.repository.notification.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val dataSource: NotificationDataSource) :
    NotificationRepository {
    override fun getNotification(receiverId: Int): Flow<List<NotificationData>> {
        return flow {
            emit(dataSource.getNotification().data.toEntity())
        }.flowOn(Dispatchers.IO)
    }

    override fun deleteNotification(notificationId: Int): Flow<NotificationDeleteData> {
        return flow {
            emit(
                dataSource.deleteNotification(notificationId).toEntity()
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun putReadNotification(notificationId: Int): Flow<List<NotificationReadData>> {
        return flow {
            emit(dataSource.putReadNotification(notificationId).toEntity())
        }.flowOn(Dispatchers.IO)
    }
}