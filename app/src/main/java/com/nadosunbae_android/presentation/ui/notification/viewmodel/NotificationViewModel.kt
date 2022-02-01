package com.nadosunbae_android.presentation.ui.notification.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.model.response.notification.ResponseNotificationDeleteData
import com.nadosunbae_android.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.model.response.notification.ResponseNotificationReadData
import com.nadosunbae_android.repositoryimpl.notification.NotificationRepository

class NotificationViewModel : ViewModel() {
    private val notificationRepository : NotificationRepository = NotificationRepositoryImpl()

    //알림탭
    //전체 알림 리스트
    var notificationList : MutableLiveData<ResponseNotificationListData> = MutableLiveData()

    //알림 삭제 데이터
    var deleteNotification : MutableLiveData<ResponseNotificationDeleteData> = MutableLiveData()

    //알림 읽기
    var notificationRead : MutableLiveData<ResponseNotificationReadData> = MutableLiveData()

    //전체 알림 리스트 보기
    fun getNotification(receiverId : Int){
        notificationRepository.getNotification(receiverId,
            onResponse = {
                notificationList.value = it.body()
                Log.d("notificationList", "전체 알림 리스트 통신 성공")
            },
            onFailure = {
                it.printStackTrace()
                Log.d("notificationList", "전체 알림 리스트 통신 실패")
            }
        )
    }

    //알림 삭제
    fun deleteNotification(notificationId : Int){
        notificationRepository.deleteNotification(notificationId,
        onResponse = {
            deleteNotification.value = it.body()
            Log.d("deleteNotification", "알림 삭제 성공")
        },
            onFailure = {
                it.printStackTrace()
                Log.d("deleteNotification", "알림 삭제 실패")
            }
        )
    }


    //알림 읽기
    fun putReadNotification(notificationId : Int){
        notificationRepository.putReadNotification(notificationId,
        onResponse = {
            notificationRead.value = it.body()
            Log.d("putNotificationRead", "알림 읽기 성공")
        },
        onFailure = {
            it.printStackTrace()
            Log.d("putNotificationRead", "알림 읽기 실패")
        })
    }

}