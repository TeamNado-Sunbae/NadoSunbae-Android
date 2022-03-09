package com.nadosunbae_android.app.presentation.ui.notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.nadosunbae_android.app.databinding.FragmentNotificationBinding
import com.nadosunbae_android.domain.model.notification.NotificationListData
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.InformationDetailActivity
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.notification.adapter.NotificationAdapter
import com.nadosunbae_android.app.presentation.ui.notification.viewmodel.NotificationViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(com.nadosunbae_android.app.R.layout.fragment_notification) {
    private val notificationViewModel: NotificationViewModel by viewModel()

    private val mainViewModel: MainViewModel by sharedViewModel()

    var link = DataToFragment()
    var mainActivity = MainActivity()
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotificationList()
        observeLoadingEnd()

    }

    //로딩 종료
    private fun observeLoadingEnd() {
        notificationViewModel.onLoadingEnd.observe(viewLifecycleOwner){
            dismissLoading()
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.signData.observe(viewLifecycleOwner) {
            notificationViewModel.getNotification(it.userId)
            mainViewModel.userId.value = it.userId
        }
    }

    //알림 리스트 조회
    private fun initNotificationList() {
        notificationAdapter = NotificationAdapter(link)
        binding.rcNotification.adapter = notificationAdapter

        mainViewModel.signData.observe(viewLifecycleOwner) {
            showLoading()
            notificationViewModel.getNotification(it.userId)
        }

        notificationViewModel.notificationList.observe(viewLifecycleOwner) {
            Timber.d("알림 정보: ${it.size}")

            initNotificationEmpty(it.size)
            notificationAdapter.setNotification(it as MutableList<NotificationListData>)
        }
    }

    inner class DataToFragment() {
        fun getNotificationId(id: Int) {
            //알림삭제
            notificationViewModel.deleteNotification(id)
            notificationViewModel.deleteNotification.observe(viewLifecycleOwner) {
                if (it.isDeleted) {
                    showLoading()
                    notificationViewModel.getNotification(mainViewModel.userId.value ?: 0)
                }
            }
        }

        //알림 이동
        fun getNotificationMove(
            postId: Int,
            notificationType: Int
        ) {
            val userId = mainViewModel.signData.value?.userId
            // 2,4 -> 질문글, 3,5 -> 정보글, 1 -> 1:1질문글
            when (notificationType) {
                2 -> {
                    val intent = Intent(requireActivity(), QuestionDetailActivity::class.java)
                    intent.apply {
                        putExtra("postId", postId)
                        putExtra("all", 1)
                        putExtra("userId", userId)
                    }
                    startActivity(intent)
                }
                4 -> {
                    val intent = Intent(requireActivity(), QuestionDetailActivity::class.java)
                    intent.apply {
                        putExtra("postId", postId)
                        putExtra("all", 1)
                        putExtra("userId", userId)
                    }
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(requireActivity(), QuestionDetailActivity::class.java)
                    intent.apply {
                        putExtra("myPageNum",1)
                        putExtra("postId", postId)
                        putExtra("all", 2)
                        putExtra("userId", userId)
                    }
                    startActivity(intent)
                }
                else -> {
                    val intent = Intent(requireActivity(), InformationDetailActivity::class.java)
                    intent.apply {
                        putExtra("postId", postId)
                        putExtra("userId", userId)
                    }
                    startActivity(intent)
                }
            }
        }

        //알림 읽기
        fun getReadNotification(notificationId: Int) {
            showLoading()
            notificationViewModel.putReadNotification(notificationId)
        }
    }


    //알림 엠티뷰
    private fun initNotificationEmpty(size : Int){
        if(size == 0){
            binding.textNotificationEmpty.visibility = View.VISIBLE
        }else{
            binding.textNotificationEmpty.visibility = View.GONE
        }
    }

}





