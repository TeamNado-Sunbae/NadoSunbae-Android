package com.nadosunbae_android.app.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySignUpFinishBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.domain.model.sign.CertificationEmailData
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFinishActivity : BaseActivity<ActivitySignUpFinishBinding>(R.layout.activity_sign_up_finish) {

    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nextPage()
        resendBtn()
    }

    private fun nextPage() {
        binding.clSignupFinish.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    private fun resendBtn() {
        binding.textSignupResend.setOnClickListener {
            initResend()
        }
    }

    //재전송
    private fun initResend() {
        val email = intent.getStringExtra("email") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        Log.d("ResendCheckEmail", email)
        Log.d("ResendCheckPassword", password)
        signUpBasicInfoViewModel.postCertificationEmail(
            CertificationEmailData(email, password)
        )
    }
}