package com.nadosunbae_android.data.datasource.remote.notification

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationReadData

interface NotificationDataSource {

    // 전체 알림 리스트 조회
    suspend fun getNotification() : Response<ResponseNotificationData>

    //알림 삭제
    suspend fun deleteNotification(
        notificationId : Int) : ResponseNotificationDeleteData

    //알림 읽기
    suspend fun putReadNotification(notificationId : Int) : ResponseNotificationReadData

}