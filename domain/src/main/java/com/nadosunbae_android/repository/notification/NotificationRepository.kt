package com.nadosunbae_android.repository.notification

import com.nadosunbae_android.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.model.response.notification.ResponseNotificationReadData
import retrofit2.Response

interface NotificationRepository {

    // 전체 알림 리스트 조회
    fun getNotification(
        receiverId : Int,
        onResponse: (Response<ResponseNotificationListData>) -> Unit,
        onFailure : (Throwable) -> Unit)

    //알림 삭제
    fun deleteNotification(
        notificationId : Int,
        onResponse: (Response<ResponseNotificationDeleteData>) -> Unit,
        onFailure : (Throwable) -> Unit)


    //알림 읽기
    fun putReadNotification(
        notificationId : Int,
        onResponse: (Response<ResponseNotificationReadData>) -> Unit,
        onFailure: (Throwable) -> Unit)

}