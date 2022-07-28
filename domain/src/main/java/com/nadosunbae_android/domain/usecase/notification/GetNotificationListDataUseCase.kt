package com.nadosunbae_android.domain.usecase.notification

import com.nadosunbae_android.domain.model.notification.NotificationListData
import com.nadosunbae_android.domain.repository.notification.NotificationRepository
import javax.inject.Inject

class GetNotificationListDataUseCase @Inject constructor(private val repository : NotificationRepository) {
    suspend operator fun invoke(receiverId : Int) : List<NotificationListData>{
        return repository.getNotification(receiverId)
    }
}