package com.nadosunbae_android.data.api.notification


import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationReadData
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationService {

    //전체 알림 리스트 조회
    @GET("notification")
    suspend fun getNotification(): Response<ResponseNotificationData>

    //알림 삭제
    @DELETE("notification/{notificationId}")
    suspend fun deleteNotification(
        @Path("notificationId") notificationId : Int
    ) : ResponseNotificationDeleteData

    //알림 읽음 처리
    @PUT("notification/{notificationId}/read")
    suspend fun putReadNotification(
        @Path("notificationId") notificationId : Int
    )  : ResponseNotificationReadData
}