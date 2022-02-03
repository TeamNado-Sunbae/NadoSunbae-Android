package com.nadosunbae_android.model.notification

import java.util.*

//전체 알림 리스트 받아오기
data class NotificationListData(
    val content: String,
    val createdAt: Date,
    val isDeleted: Boolean,
    val isRead: Boolean,
    val isQuestionToPerson: Boolean,
    val notificationId: Int,
    val notificationType: Int,
    val postId: Int,
    val nickname: String,
    val profileImageId: Int,
    val senderId: Int

)

