package com.nadosunbae_android.usecase.notification

import com.nadosunbae_android.model.notification.NotificationReadData
import com.nadosunbae_android.repository.notification.NotificationRepository

class ReadNotificationUseCase(private val repository : NotificationRepository) {
    suspend operator fun invoke(notificationId : Int) : NotificationReadData{
        return repository.putReadNotification(notificationId)
    }
}