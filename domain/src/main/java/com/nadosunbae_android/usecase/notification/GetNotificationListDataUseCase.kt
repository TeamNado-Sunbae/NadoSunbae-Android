package com.nadosunbae_android.usecase.notification

import com.nadosunbae_android.model.notification.NotificationListData
import com.nadosunbae_android.repository.notification.NotificationRepository

class GetNotificationListDataUseCase(private val repository : NotificationRepository) {
    suspend operator fun invoke(receiverId : Int) : List<NotificationListData>{
        return repository.getNotification(receiverId)
    }
}