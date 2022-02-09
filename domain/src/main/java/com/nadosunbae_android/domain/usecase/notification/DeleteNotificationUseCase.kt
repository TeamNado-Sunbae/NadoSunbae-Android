package com.nadosunbae_android.domain.usecase.notification

import com.nadosunbae_android.data.model.notification.NotificationDeleteData
import com.nadosunbae_android.domain.repository.notification.NotificationRepository

class DeleteNotificationUseCase(private val repository : NotificationRepository) {

    suspend operator fun invoke(notificationId : Int) : NotificationDeleteData{
        return repository.deleteNotification(notificationId)
    }
}