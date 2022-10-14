package com.nadosunbae_android.domain.model.notification

import java.time.LocalDate
import java.util.*

//전체 알림 리스트 받아오기
data class NotificationData(
    val commentId: Int,
    val content: String,
    val createdAt: Date?,
    val isRead: Boolean,
    val notificationId: Int,
    val notificationTypeId: Int,
    val postId: Int,
    val senderNickname: String?,
    val profileImageId: Int,
    val senderId: Int
) {
    companion object {
        val DEFAULT = NotificationData(
            -1, "",
            null, false, 0, 8, 0, "", 0, 0
        )
    }
}

