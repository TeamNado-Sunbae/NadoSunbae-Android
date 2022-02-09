package com.nadosunbae_android.app.presentation.ui.notification.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.domain.model.notification.NotificationDeleteData
import com.nadosunbae_android.domain.model.notification.NotificationListData
import com.nadosunbae_android.domain.model.notification.NotificationReadData
import com.nadosunbae_android.domain.usecase.notification.DeleteNotificationUseCase
import com.nadosunbae_android.domain.usecase.notification.GetNotificationListDataUseCase
import com.nadosunbae_android.domain.usecase.notification.ReadNotificationUseCase
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationListDataUseCase: GetNotificationListDataUseCase,
    private val deleteNotificationUseCase : DeleteNotificationUseCase,
    private val readNotificationUseCase : ReadNotificationUseCase
) : ViewModel() {

    //알림탭
    //전체 알림 리스트
    var notificationList : MutableLiveData<List<NotificationListData>> = MutableLiveData()

    //알림 삭제 데이터
    var deleteNotification : MutableLiveData<NotificationDeleteData> = MutableLiveData()

    //알림 읽기
    var notificationRead : MutableLiveData<NotificationReadData> = MutableLiveData()

    //전체 알림 리스트 보기
    fun getNotification(receiverId : Int){
        viewModelScope.launch {
            runCatching { getNotificationListDataUseCase(receiverId) }
                .onSuccess {
                    notificationList.value = it
                    Log.d("notificationList", "전체 알림 리스트 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("notificationList", "전체 알림 리스트 통신 실패")
                }
        }
    }

    //알림 삭제
    fun deleteNotification(notificationId : Int){
        viewModelScope.launch {
            runCatching { deleteNotificationUseCase(notificationId) }
                .onSuccess {
                    deleteNotification.value = it
                    Log.d("deleteNotification", "알림 삭제 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("deleteNotification", "알림 삭제 실패")
                }
        }
    }


    //알림 읽기
    fun putReadNotification(notificationId : Int){
        viewModelScope.launch {
            runCatching { readNotificationUseCase(notificationId) }
                .onSuccess {
                    notificationRead.value = it
                    Log.d("putNotificationRead", "알림 읽기 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("putNotificationRead", "알림 읽기 실패")
                }
        }
    }

}