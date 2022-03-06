package com.nadosunbae_android.app.presentation.ui.sign

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySignInBinding
import com.nadosunbae_android.domain.model.sign.SignInItem
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.util.NadoSunBaeSharedPreference
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.getScopeId
import org.koin.core.component.getScopeName
import timber.log.Timber

class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private lateinit var mainActivity: MainActivity
    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()

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
        setupTimber()
        observeSignIn()

    }
    //Timber 초기화
    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
    //id editText textwatcher
    private fun onViewId() {

        binding.etSignInId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.imgSignInIdCancel.isSelected = binding.etSignInId.text.toString() != ""
                signUpBasicInfoViewModel.email.value = p0.toString()
                isEmptyText()
            }
        })

        binding.imgSignInIdCancel.setOnClickListener {
            binding.etSignInId.setText(null)
            binding.clLogin.isSelected = false
        }
    }

    //pw editText textWatcher
    private fun onViewPw() {
        binding.etSignInPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.imgSignInPwCancel.isSelected = binding.etSignInPw.text.toString() != ""
                signUpBasicInfoViewModel.password.value = p0.toString()
                isEmptyText()

            }
        })

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
            startActivity(Intent(this, SignUpAgreementActivity::class.java))
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
                Log.d("deviceToken", "디바이스 토큰 정보 가저오기 실패", task.exception)
                return@OnCompleteListener
            }

            val token = task.result
            signUpBasicInfoViewModel.deviceToken.value = token
            Log.d("token", token)

        })
    }


    private fun observeSignIn() {
        signUpBasicInfoViewModel.signIn.observe(this) {
            dismissLoading()

            if (it.success) {
                Log.d(TAG, "access token : ${it.accessToken}")
                Log.d(TAG, "refresh token : ${it.refreshToken}")
                Log.d(TAG, "--- Login Success ---")
                NadoSunBaeSharedPreference.setAccessToken(this, it.accessToken)
                NadoSunBaeSharedPreference.setRefreshToken(this, it.refreshToken)
                val intent = Intent(this, MainActivity::class.java)
                val data = it.user
                intent.apply {
                    putExtra("signData", data)
                }
                startActivity(intent)
                finish()
            }
            else {
                binding.textSignInWarn.visibility = View.VISIBLE
                NadoSunBaeSharedPreference.setUserId(this, it.user.userId)
                Log.d(TAG, " --- Login Failed ---")
            }

        }
    }



    //로그인 버튼 클릭 이벤트
    private fun moveMainPage() {
        Log.d("SignUp", "서버 통신 성공!")

        binding.clLogin.setOnClickListener {
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