package com.nadosunbae_android.app.presentation.ui.notification

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.databinding.FragmentNotificationBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.community.CommunityDetailActivity
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.notification.adapter.NotificationAdapter
import com.nadosunbae_android.app.presentation.ui.notification.viewmodel.NotificationViewModel
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(com.nadosunbae_android.app.R.layout.fragment_notification) {
    private val notificationViewModel: NotificationViewModel by viewModels()


    var mainActivity = MainActivity()
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotificationList()
        observeLoadingEnd()
        submitAnalytics()
        getReadNotification()
        deleteNotification()
    }

    override fun onResume() {
        super.onResume()
        notificationViewModel.getNotification()
    }

    //로딩 종료
    private fun observeLoadingEnd() {
        notificationViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            dismissLoading()
        }
    }


    //알림 리스트 조회
    private fun initNotificationList() {
        notificationAdapter = NotificationAdapter()
        binding.rcNotification.adapter = notificationAdapter
        notificationViewModel.notificationList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                binding.sizeCheck = it.isEmpty()
                if (it[0].commentId != 0) {
                    notificationAdapter.submitList(it)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    //알림 이동
    fun getNotificationMove(
        postId: Int,
        notificationType: Int
    ) {
        // 2,4 -> 질문글, 3,5 -> 정보글, 1,6,7 -> 1:1질문글 8,9 -> 커뮤니티
        when (notificationType) {
            2, 4 -> {
                setRestrictDialog {
                    val intent =
                        Intent(requireActivity(), QuestionDetailActivity::class.java)
                    intent.apply {
                        putExtra("postId", postId)
                        putExtra("all", 1)
                        putExtra("userId", MainGlobals.signInData?.userId)
                    }
                    startActivity(intent)
                }


            }
            1, 6, 7 -> {
                setRestrictDialog {
                    val intent =
                        Intent(requireActivity(), QuestionDetailActivity::class.java)
                    intent.apply {
                        putExtra("myPageNum", 1)
                        putExtra("postId", postId)
                        putExtra("all", 2)
                        putExtra("userId", MainGlobals.signInData?.userId)
                    }
                }
            }
            else -> {
                setRestrictDialog {
                    val intent =
                        Intent(requireActivity(), CommunityDetailActivity::class.java)
                    intent.apply {
                        putExtra("postId", postId.toString())
                    }
                    startActivity(intent)
                }
            }
        }
    }

    //다이얼로그 띄우는 공통 부분
    private fun setRestrictDialog(behaviorAction: () -> Unit) {
        CustomDialog(requireActivity()).restrictDialog(
            requireActivity(),
            ReviewGlobals.isReviewed,
            MainGlobals.signInData!!.isUserReported,
            MainGlobals.signInData!!.isReviewInappropriate,
            MainGlobals.signInData?.message.toString(),
            behavior = {
                behaviorAction()
            })
    }


    //알림 읽기
    private fun getReadNotification() {
        showLoading()
        notificationAdapter.setItemClickListener { notificationId, postId, notificationTypeId ->
            notificationViewModel.putReadNotification(notificationId)
            getNotificationMove(postId,notificationTypeId)
        }
    }

    //알림 삭제
    private fun deleteNotification() {
        notificationAdapter.setDeleteClickListener {
            notificationViewModel.deleteNotification(it)
        }
    }


    private fun submitAnalytics() {
        FirebaseAnalyticsUtil.selectTab(FirebaseAnalyticsUtil.Tab.NOTIFICATION)
    }

}





