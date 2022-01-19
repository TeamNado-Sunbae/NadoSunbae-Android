package com.nadosunbae_android.presentation.ui.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.FragmentNotificationBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.notification.adapter.NotificationAdapter
import com.nadosunbae_android.presentation.ui.notification.viewmodel.NotificationViewModel


class NotificationFragment : BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {
    private lateinit var notificationAdapter : NotificationAdapter
    private val notificationViewModel: NotificationViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotificationViewModel() as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }




    //전체 알림 리스트 조회
    fun initNotificationList(){
        notificationViewModel.getNotification(3)

    }
}