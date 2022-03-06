package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityNotificationSettingBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity


class NotificationSettingActivity :
    BaseActivity<ActivityNotificationSettingBinding>(R.layout.activity_notification_setting) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alarmSetting()
        initNotification()
        backBtn()

    }


    //버튼 토글 변경 리스너
    private fun alarmSetting() {
        binding.imgAlarm.setOnClickListener {
            binding.imgAlarm.isSelected = !binding.imgAlarm.isSelected
            checkAlarm()
        }
    }

    //뒤로가기 버튼
    private fun backBtn() {
        binding.imgMypageAlarmMovePage.setOnClickListener {
            finish()
        }
    }


    //버전에 따라 설정창 이동
    private fun checkAlarm() {
        val intent = Intent()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, this?.packageName)
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", this?.packageName)
                intent.putExtra("app_uid", this?.applicationInfo?.uid)
                NotificationManagerCompat.from(this).areNotificationsEnabled()
            }

            else -> {
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("package:" + this?.packageName)
            }
        }

        this?.startActivity(intent)

    }


    //설정 갔다가 다시 앱으로 돌아왔을 때 반영
    override fun onRestart() {
        super.onRestart()
        initNotification()
    }


    //설정에서 알림 받을지 선택한지에 따라 버튼 토글 변경
    private fun initNotification() {
        if (NotificationManagerCompat.from(this).areNotificationsEnabled() == true) {
            binding.imgAlarm.isSelected = true

        } else if (NotificationManagerCompat.from(this).areNotificationsEnabled() == false) {
            binding.imgAlarm.isSelected = false

        }
    }

}