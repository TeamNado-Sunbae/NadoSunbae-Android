package com.nadosunbae_android.api.notification


import com.nadosunbae_android.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.model.response.notification.ResponseNotificationReadData
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationService {

    //전체 알림 리스트 조회
    @GET("notification/list/{receiverId}")
    suspend fun getNotification(
        @Path("receiverId") receiverId: Int
    ): ResponseNotificationListData

    //알림 삭제
    @DELETE("notification/{notificationId}")
    suspend fun deleteNotification(
        @Path("notificationId") notificationId : Int
    ) : ResponseNotificationDeleteData

    //알림 읽음 처리
    @PUT("notification/read/{notificationId}")
    suspend fun putReadNotification(
        @Path("notificationId") notificationId : Int
    )  : ResponseNotificationReadData
}