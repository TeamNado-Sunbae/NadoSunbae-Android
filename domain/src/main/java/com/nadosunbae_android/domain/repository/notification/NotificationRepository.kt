package com.nadosunbae_android.domain.repository.notification

import com.nadosunbae_android.domain.model.notification.NotificationData
import com.nadosunbae_android.domain.model.notification.NotificationDeleteData
import com.nadosunbae_android.domain.model.notification.NotificationReadData
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    // 전체 알림 리스트 조회
    fun getNotification(): Flow<List<NotificationData>>


    //알림 삭제
    fun deleteNotification(notificationId: Int): Flow<NotificationDeleteData>


    //알림 읽기
    fun putReadNotification(notificationId: Int): Flow<List<NotificationReadData>>

}