package com.nadosunbae_android.data.model.response.notification

data class ResponseNotificationListData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val notificationList: List<Notification>
    ) {
        data class Notification(
            val content: String,
            val createdAt: String,
            val isDeleted: Boolean,
            val isRead: Boolean,
            val notificationId: Int,
            val notificationType: Int,
            val postId: Int,
            val sender: Sender
        ) {
            data class Sender(
                val nickname: String,
                val profileImageId: Int,
                val senderId: Int
            )
        }
    }
}