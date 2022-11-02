package com.nadosunbae_android.app.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySignUpFinishBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.sign.CertificationEmailData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpFinishActivity : BaseActivity<ActivitySignUpFinishBinding>(R.layout.activity_sign_up_finish) {

    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nextPage()
        resendBtn()
    }

    private fun nextPage() {
        binding.clSignupFinish.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            FirebaseAnalyticsUtil.firebaseLog("signup_process",
                "journey", "signup_success")
            finish()
        }
    }

    private fun resendBtn() {
        binding.textSignupResend.setOnClickListener {
            initResend()
            FirebaseAnalyticsUtil.firebaseLog("remail_button","type","sign_up_view")
        }
    }

    //재전송
    private fun initResend() {
        val email = intent.getStringExtra("email") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        Timber.d("ResendCheckEmail: $email")
        Timber.d("ResendCheckPassword: $password")
        signUpBasicInfoViewModel.postCertificationEmail(
            CertificationEmailData(email, password)
        )
    }
}