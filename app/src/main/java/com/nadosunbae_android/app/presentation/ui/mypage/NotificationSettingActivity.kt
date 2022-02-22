package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityNotificationSettingBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity

class NotificationSettingActivity : BaseActivity<ActivityNotificationSettingBinding>(R.layout.activity_notification_setting) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSetting()
        alarmSetting()
        backBtn()

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
            finish()
        }
    }
}