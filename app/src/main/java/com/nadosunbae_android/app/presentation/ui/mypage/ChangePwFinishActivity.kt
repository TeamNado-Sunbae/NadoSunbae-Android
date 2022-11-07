package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityChangePwFinishBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.mypage.MyPageResetPasswordItem
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ChangePwFinishActivity :
    BaseActivity<ActivityChangePwFinishBinding>(R.layout.activity_change_pw_finish) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoginBtn()
        initResendBtn()

    }

    private fun initLoginBtn() {
        binding.clChangePwFinish.setOnClickListener {
            finish()
        }
    }

    private fun initResendBtn() {
        binding.textChangePwResend.setOnClickListener {
            FirebaseAnalyticsUtil.firebaseLog("remail_button","type","find_password_view")
            val email = intent.getStringExtra("email") ?: ""
            Timber.d("ChangePwFinishEmailCheck $email")
            myPageViewModel.postMyPageRestPassword(MyPageResetPasswordItem(email))
        }
    }
}