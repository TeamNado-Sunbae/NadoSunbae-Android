package com.nadosunbae_android.presentation.ui.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.databinding.FragmentNotificationBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.notification.adapter.NotificationAdapter
import com.nadosunbae_android.presentation.ui.notification.viewmodel.NotificationViewModel


class NotificationFragment : BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {
    private val notificationViewModel: NotificationViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotificationViewModel() as T
            }
        }
    }

    var link = DataToFragment()

    private lateinit var notificationAdapter : NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotificationList()
    }


    //알림 리스트 조회
    private fun initNotificationList(){
        notificationAdapter = NotificationAdapter(link)
        binding.rcNotification.adapter = notificationAdapter
        notificationViewModel.getNotification(3)
        notificationViewModel.notificationList.observe(viewLifecycleOwner){
            notificationAdapter.setNotification(it.data.notificationList as MutableList<ResponseNotificationListData.Data.Notification>)
        }
    }

    inner class DataToFragment(){
        fun getNotificationId(id : Int){
            //알림삭제
            notificationViewModel.deleteNotification(id)
            notificationViewModel.deleteNotification.observe(viewLifecycleOwner){
                if(it.data.isDeleted){
                    notificationViewModel.getNotification(3)
                }
            }
        }

    }
}