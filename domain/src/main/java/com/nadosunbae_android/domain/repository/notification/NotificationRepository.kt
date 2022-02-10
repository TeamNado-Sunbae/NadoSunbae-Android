package com.nadosunbae_android.domain.repository.notification

import com.nadosunbae_android.domain.model.notification.NotificationDeleteData
import com.nadosunbae_android.domain.model.notification.NotificationListData
import com.nadosunbae_android.domain.model.notification.NotificationReadData

interface NotificationRepository {

    // 전체 알림 리스트 조회
    suspend fun getNotification(receiverId : Int) : List<NotificationListData>


    //알림 삭제
    suspend fun deleteNotification(notificationId : Int) : NotificationDeleteData


    //알림 읽기
    suspend fun putReadNotification(notificationId : Int) : NotificationReadData

}