package com.nadosunbae_android.domain.model.notification

data class NotificationDeleteData(
    val isDeleted: Boolean,
    val notificationId: Int
){
    companion object{
        val DEFAULT = NotificationDeleteData(
            false,0
        )
    }
}
