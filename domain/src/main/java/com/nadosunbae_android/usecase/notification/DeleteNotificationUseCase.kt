package com.nadosunbae_android.usecase.notification

import com.nadosunbae_android.model.notification.NotificationDeleteData
import com.nadosunbae_android.repository.notification.NotificationRepository

class DeleteNotificationUseCase(private val repository : NotificationRepository) {

    suspend operator fun invoke(notificationId : Int) : NotificationDeleteData{
        return repository.deleteNotification(notificationId)
    }
}