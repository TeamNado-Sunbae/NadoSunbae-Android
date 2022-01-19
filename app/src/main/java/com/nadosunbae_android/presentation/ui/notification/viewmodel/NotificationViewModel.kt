package com.nadosunbae_android.presentation.ui.notification.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.data.repository.notification.NotificationRepository
import com.nadosunbae_android.data.repository.notification.NotificationRepositoryImpl

class NotificationViewModel : ViewModel() {
    private val notificationRepository : NotificationRepository = NotificationRepositoryImpl()

    //알림탭
    //전체 알림 리스트
    var notificationList : MutableLiveData<ResponseNotificationListData> = MutableLiveData()

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


}