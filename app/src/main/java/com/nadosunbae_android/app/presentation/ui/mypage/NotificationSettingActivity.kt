package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityNotificationSettingBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationSettingActivity : BaseActivity<ActivityNotificationSettingBinding>(R.layout.activity_notification_setting) {

    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLoadingEnd()
        initSetting()
        alarmSetting()
        backBtn()

    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }

    private fun initSetting() {
        binding.imgAlarm.isSelected = true
    }

    private fun alarmSetting() {
        binding.imgAlarm.setOnClickListener {
            binding.imgAlarm.isSelected = !binding.imgAlarm.isSelected
        }
    }

    private fun backBtn() {
        binding.imgMypageAlarmMovePage.setOnClickListener {
            showLoading()
            finish()
        }
    }
}