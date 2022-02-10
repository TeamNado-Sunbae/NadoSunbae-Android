package com.nadosunbae_android.model.response.notification

data class ResponseNotificationDeleteData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val isDeleted: Boolean,
        val notificationId: Int
    )
}