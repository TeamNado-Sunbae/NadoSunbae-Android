package com.nadosunbae_android.data.api.notification


import com.nadosunbae_android.data.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationListData
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {

    //전체 알림 리스트 조회
    @GET("notification/list/{receiverId}")
    fun getNotification(
        @Path("receiverId") receiverId: Int
    ): Call<ResponseNotificationListData>

    //알림 삭제
    @DELETE("notification/{notificationId}")
    fun deleteNotification(
        @Path("notificationId") notificationId : Int
    ) : Call<ResponseNotificationDeleteData>
}