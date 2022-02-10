package com.nadosunbae_android.data.mapper.notification

import com.nadosunbae_android.domain.model.notification.NotificationDeleteData
import com.nadosunbae_android.domain.model.notification.NotificationListData
import com.nadosunbae_android.domain.model.notification.NotificationReadData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationReadData

object NotificationMapper {


    //전체 알림 리스트 받아오기
    fun mapperToNotificationListData(responseNotificationListData: ResponseNotificationListData): List<NotificationListData> {
        return responseNotificationListData.data.notificationList.map {
            NotificationListData(
                content = it.content,
                createdAt = it.createdAt,
                isDeleted = it.isDeleted,
                isRead = it.isRead,
                isQuestionToPerson = it.isQuestionToPerson,
                notificationId = it.notificationId,
                notificationType = it.notificationType,
                postId = it.postId,
                nickname = it.sender.nickname,
                profileImageId = it.sender.profileImageId,
                senderId = it.sender.senderId
            )

        }
    }

    //알림 삭제
    fun mapperToNotificationDeleteData(responseNotificationDeleteData: ResponseNotificationDeleteData) : NotificationDeleteData{
        return NotificationDeleteData(
            isDeleted = responseNotificationDeleteData.data.isDeleted,
            notificationId = responseNotificationDeleteData.data.notificationId
        )
    }

    //알림 읽었는지 확인
    fun mapperToNotificationReadData(responseNotificationReadData: ResponseNotificationReadData) : NotificationReadData{
        return NotificationReadData(
            createdAt = responseNotificationReadData.data.createdAt,
        isDeleted = responseNotificationReadData.data.isDeleted,
        isRead = responseNotificationReadData.data.isRead,
        notificationId = responseNotificationReadData.data.notificationId,
        receiverId = responseNotificationReadData.data.receiverId,
        updatedAt = responseNotificationReadData.data.updatedAt
        )

    }

}