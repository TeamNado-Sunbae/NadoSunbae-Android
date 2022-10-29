package com.nadosunbae_android.app.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySignInBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.app.util.NadoSunBaeSharedPreference
import com.nadosunbae_android.domain.model.sign.CertificationEmailData
import com.nadosunbae_android.domain.model.sign.SignInItem
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    companion object {
        private val TAG = SignInActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        deviceToken()
        onViewId()
        moveFindPw()
        moveSignUp()
        onViewPw()
        moveQeustionPage()
        observeSignIn()
        observeLoadingEnd()

    }

    private fun observeLoadingEnd() {
        signUpBasicInfoViewModel.onLoadingEnd.observe(this) {
            dismissLoading()

        }
    }


    //id editText textwatcher
    private fun onViewId() {

        binding.etSignInId.addTextChangedListener {
            binding.imgSignInIdCancel.isSelected = binding.etSignInId.text.toString() != ""
            signUpBasicInfoViewModel.email.value = it.toString()
            isEmptyText()
        }

        binding.imgSignInIdCancel.setOnClickListener {
            binding.etSignInId.setText(null)
            binding.clLogin.isSelected = false
        }
    }

    //pw editText textWatcher
    private fun onViewPw() {
        binding.etSignInPw.addTextChangedListener {
            binding.imgSignInPwCancel.isSelected = binding.etSignInPw.text.toString() != ""
            signUpBasicInfoViewModel.password.value = it.toString()
            isEmptyText()
        }

        binding.imgSignInPwCancel.setOnClickListener {
            binding.etSignInPw.setText(null)
            binding.clLogin.isSelected = false

        }
    }

    //id editText & pw editText 빈칸 여부
    private fun isEmptyText() {
        if (binding.etSignInId.text.toString() != "" && binding.etSignInPw.text.toString() != "") {
            binding.clLogin.isSelected = true
            moveMainPage()
        } else {
            binding.clLogin.isSelected = false
        }
    }

    //회원가입 페이지로 이동
    private fun moveSignUp() {
        binding.textSignInSignup.setOnClickListener {
            startActivity(Intent(this, SignUpMainActivity::class.java))
            FirebaseAnalyticsUtil.firebaseLog("signup_process",
            "journey", "signup_start")
        }
    }

    private fun moveQeustionPage() {
        mainViewModel.getAppLink()
        binding.textSignInQuestion.setOnClickListener {
            mainViewModel.appLink.observe(this) {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("url", it.data.kakaoTalkChannel)
                startActivity(intent)
            }
        }
    }


    //비밀번호 찾기 페이지로 이동
    private fun moveFindPw() {
        binding.textSignInFindpw.setOnClickListener {
            startActivity(Intent(this, FindPwActivity::class.java))
        }
    }

    // 디바이스 등록
    private fun deviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.d("디바이스 토큰 정보 가저오기 실패: ${task.exception}")
                return@OnCompleteListener
            }

            val token = task.result
            signUpBasicInfoViewModel.deviceToken.value = token
            Timber.d("deviceToken: ${token}")

        })
    }


    private fun observeSignIn() {

        dismissLoading()

        signUpBasicInfoViewModel.signInStatus.observe(this) {
            if (it == 200) {
                Timber.d("testest : signUpBasicInfoViewModel.signIn.value?.user?.userId")
                Timber.d(
                    "access token : ${signUpBasicInfoViewModel.signIn.value?.accessToken}"
                )
                Timber.d(
                    "refresh token : ${signUpBasicInfoViewModel.signIn.value?.refreshToken}"
                )
                Timber.d("--- Login Success ---")
                NadoSunBaeSharedPreference.setAccessToken(
                    this,
                    signUpBasicInfoViewModel.signIn.value?.accessToken ?: ""
                )
                NadoSunBaeSharedPreference.setRefreshToken(
                    this,
                    signUpBasicInfoViewModel.signIn.value?.refreshToken ?: ""
                )
                val intent = Intent(this, MainActivity::class.java)
                val data = signUpBasicInfoViewModel.signIn.value?.user
                Timber.d("signInData : $data")
                intent.apply {
                    putExtra("signData", data)
                }
                startActivity(intent)
                finish()

            } else if (it == 202) {
                certificationAlert()
                Timber.d(" --- Email Certification ---")

            } else {
                binding.textSignInWarn.visibility = View.VISIBLE
                Timber.d(" --- Login Failed ---")
            }
        }
    }

    //재전송
    private fun initResend() {
        val email = binding.etSignInId.text.toString()
        val password = binding.etSignInPw.text.toString()
        signUpBasicInfoViewModel.postCertificationEmail(
            CertificationEmailData(email, password)
        )
    }

    //메일 인증 알럿
    private fun certificationAlert() {
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                getString(R.string.email_certification_title),
                getString(R.string.email_certification_email),
                getString(R.string.email_certification_close)
            ),
            complete = {
                initResend()
            },
            cancel = {

            }
        )
    }


    //로그인 버튼 클릭 이벤트
    private fun moveMainPage() {
        binding.clLogin.setOnClickListener {
            showLoading()
            signUpBasicInfoViewModel.signIn(
                SignInItem(
                    signUpBasicInfoViewModel.email.value.toString(),
                    signUpBasicInfoViewModel.password.value.toString(),
                    signUpBasicInfoViewModel.deviceToken.value.toString()
                )
            )
        }
    }
}
