package com.nadosunbae_android.data.datasource.remote.notification

import com.nadosunbae_android.domain.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.domain.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.domain.model.response.notification.ResponseNotificationReadData

interface NotificationDataSource {

    // 전체 알림 리스트 조회
    suspend fun getNotification(
        receiverId : Int) : ResponseNotificationListData

    //알림 삭제
    suspend fun deleteNotification(
        notificationId : Int) : ResponseNotificationDeleteData

    //알림 읽기
    suspend fun putReadNotification(notificationId : Int) : ResponseNotificationReadData

}