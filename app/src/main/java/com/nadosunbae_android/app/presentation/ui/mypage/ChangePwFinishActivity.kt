package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityChangePwFinishBinding
import com.nadosunbae_android.app.databinding.ActivitySignUpBasicInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.sign.SignInActivity
import com.nadosunbae_android.domain.model.mypage.MyPageResetPasswordItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePwFinishActivity :
    BaseActivity<ActivityChangePwFinishBinding>(R.layout.activity_change_pw_finish) {

    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoginBtn()
        initResendBtn()

    }

    private fun initLoginBtn() {
        binding.clChangePwFinish.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    private fun initResendBtn() {
        binding.textChangePwResend.setOnClickListener {
            val email = intent.getStringExtra("email") ?: ""
            Log.d("ChangePwFinishEmailCheck", email)
            myPageViewModel.postMyPageRestPassword(MyPageResetPasswordItem(email))
        }
    }
}