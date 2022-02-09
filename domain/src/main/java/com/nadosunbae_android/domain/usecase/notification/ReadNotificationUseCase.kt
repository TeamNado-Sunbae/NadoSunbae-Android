package com.nadosunbae_android.domain.usecase.notification

import com.nadosunbae_android.data.model.notification.NotificationReadData
import com.nadosunbae_android.domain.repository.notification.NotificationRepository

class ReadNotificationUseCase(private val repository : NotificationRepository) {
    suspend operator fun invoke(notificationId : Int) : NotificationReadData{
        return repository.putReadNotification(notificationId)
    }
}