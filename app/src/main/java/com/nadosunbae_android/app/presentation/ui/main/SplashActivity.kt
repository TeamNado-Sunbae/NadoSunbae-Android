package com.nadosunbae_android.app.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySplashBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.SplashViewModel
import com.nadosunbae_android.app.presentation.ui.onboarding.OnBoardingActivity
import com.nadosunbae_android.app.presentation.ui.sign.SignInActivity
import com.nadosunbae_android.app.util.NadoSunBaeSharedPreference
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel: SplashViewModel by viewModel()

    private var loginSuccess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeSignIn()
        autoLogin()
        startLoading()

    }



    private fun observeSignIn() {
        splashViewModel.signIn.observe(this) {
            if (it.success) {
                loginSuccess = true
                NadoSunBaeSharedPreference.setAccessToken(this, it.accessToken)
                NadoSunBaeSharedPreference.setRefreshToken(this, it.refreshToken)
            }
        }
    }

    private fun autoLogin() {
        val refreshToken = NadoSunBaeSharedPreference.getRefreshToken(this)

        if (refreshToken != null && refreshToken.isNotEmpty())
            splashViewModel.postRenewalToken(refreshToken)
    }



    private fun startLoading() {
        val pref = getSharedPreferences("isFirst", MODE_PRIVATE)
        val first = pref.getBoolean("isFirst", false)

        //앱 최초 실행일 때
        if (first == false) {
            Log.d("FirstTimeCheck", ": true")
            val editor = pref.edit()
            editor.putBoolean("isFirst", true)
            editor.commit()
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }

        //앱 최초 실행 아닐 때
        else {
            Log.d("FirstTimeCheck", " : false")
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = if (loginSuccess) {
                    Intent(this, MainActivity::class.java).apply {
                        putExtra("signData", splashViewModel.signIn.value?.user)
                    }
                }
                else { Intent(this, SignInActivity::class.java) }

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, DURATION)
        }
    }



    companion object {
        private const val DURATION: Long = 2000
    }
}