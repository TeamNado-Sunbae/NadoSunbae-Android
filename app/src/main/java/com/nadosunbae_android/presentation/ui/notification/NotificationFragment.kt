package com.nadosunbae_android.presentation.ui.notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.databinding.FragmentNotificationBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.InformationDetailActivity
import com.nadosunbae_android.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.presentation.ui.main.MainActivity
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.notification.adapter.NotificationAdapter
import com.nadosunbae_android.presentation.ui.notification.viewmodel.NotificationViewModel


class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {
    private val notificationViewModel: NotificationViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NotificationViewModel() as T
            }
        }
    }
    private val mainViewModel: MainViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }
    var link = DataToFragment()
    var mainActivity = MainActivity()
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotificationList()


    }


    //알림 리스트 조회
    private fun initNotificationList() {
        notificationAdapter = NotificationAdapter(link)
        binding.rcNotification.adapter = notificationAdapter
        notificationViewModel.getNotification(3)
        notificationViewModel.notificationList.observe(viewLifecycleOwner) {
            notificationAdapter.setNotification(it.data.notificationList as MutableList<ResponseNotificationListData.Data.Notification>)
        }
    }

    inner class DataToFragment() {
        fun getNotificationId(id: Int) {
            //알림삭제
            notificationViewModel.deleteNotification(id)
            notificationViewModel.deleteNotification.observe(viewLifecycleOwner) {
                if (it.data.isDeleted) {
                    notificationViewModel.getNotification(3)
                }
            }
        }

        //알림 이동
        fun getNotificationMove(postId: Int, isQuestionToPerson: Boolean, notificationType: Int) {
            Log.d(
                "notificationKing",
                postId.toString() + isQuestionToPerson.toString() + notificationType.toString()
            )
            if (isQuestionToPerson) {
                when (notificationType) {
                    2 or 4 -> {
                        val intent = Intent(requireActivity(), QuestionDetailActivity::class.java)
                        intent.putExtra("postId", postId)
                        startActivity(intent)
                    }
                    1 -> {
                        mainViewModel.classRoomFragmentNum.value = 6
                    }
                    else -> {
                        val intent = Intent(requireActivity(), InformationDetailActivity::class.java)
                        intent.putExtra("postId", postId)
                        startActivity(intent)
                    }
                }
            } else {
                if (notificationType == 2 or 4) {
                    Log.d("noti", notificationType.toString())
                    mainViewModel.classRoomFragmentNum.value = 1
                    val roomMenu = mainActivity.binding.btNvMain.selectedItemId = R.id.navigation_room
                    mainViewModel.classRoomNum.value = 1

                } else {
                    mainViewModel.classRoomFragmentNum.value = 1
                    mainViewModel.classRoomNum.value = 2
                }
            }

        }

    }
}


