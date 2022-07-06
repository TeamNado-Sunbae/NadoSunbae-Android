package com.nadosunbae_android.domain.usecase.notification

import com.nadosunbae_android.domain.model.notification.NotificationReadData
import com.nadosunbae_android.domain.repository.notification.NotificationRepository
import javax.inject.Inject

class ReadNotificationUseCase @Inject constructor(private val repository : NotificationRepository) {
    suspend operator fun invoke(notificationId : Int) : NotificationReadData{
        return repository.putReadNotification(notificationId)
    }
}