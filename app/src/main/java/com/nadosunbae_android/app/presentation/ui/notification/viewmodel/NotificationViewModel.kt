package com.nadosunbae_android.app.presentation.ui.notification.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.notification.NotificationData
import com.nadosunbae_android.domain.repository.notification.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()


    //알림탭
    //전체 알림 리스트
    private var _notificationList = MutableStateFlow(listOf(NotificationData.DEFAULT))
    val notificationList: StateFlow<List<NotificationData>>
        get() = _notificationList


    //전체 알림 리스트 보기
    fun getNotification() {
        viewModelScope.launch {
            notificationRepository.getNotification()
                .catch {
                    Timber.d("알림 리스트 받아오기 실패")
                }
                .collectLatest {
                    _notificationList.value = it
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    //알림 삭제
    fun deleteNotification(notificationId: Int) {
        viewModelScope.launch {
            notificationRepository.deleteNotification(notificationId)
                .catch {
                    Timber.d("알림 삭제 실패")
                }
                .collectLatest {
                    getNotification()
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }


    //알림 읽기
    fun putReadNotification(notificationId: Int) {
        viewModelScope.launch {
            notificationRepository.putReadNotification(notificationId)
                .catch {
                    Timber.d("알림 읽기 실패")
                }
                .collectLatest {
                    getNotification()
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }
}