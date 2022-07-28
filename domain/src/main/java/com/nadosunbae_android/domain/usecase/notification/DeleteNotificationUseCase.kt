package com.nadosunbae_android.domain.usecase.notification

import com.nadosunbae_android.domain.model.notification.NotificationDeleteData
import com.nadosunbae_android.domain.repository.notification.NotificationRepository
import javax.inject.Inject

class DeleteNotificationUseCase @Inject constructor(private val repository : NotificationRepository) {

    suspend operator fun invoke(notificationId : Int) : NotificationDeleteData{
        return repository.deleteNotification(notificationId)
    }
}