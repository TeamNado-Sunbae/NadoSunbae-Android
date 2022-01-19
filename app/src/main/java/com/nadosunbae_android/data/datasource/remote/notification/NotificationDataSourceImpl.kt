package com.nadosunbae_android.data.datasource.remote.notification

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class NotificationDataSourceImpl : NotificationDataSource {

    override fun getNotification(
        receiverId: Int,
        onResponse: (Response<ResponseNotificationListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.notificationService.getNotification(receiverId).enqueueUtil(
            onResponse, onFailure
        )
    }
}