package com.nadosunbae_android.app.presentation.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.sign.SignInData
import com.nadosunbae_android.domain.usecase.sign.PostRenewalTokenUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashViewModel(
    private val postRenewalTokenUseCase: PostRenewalTokenUseCase
) : ViewModel() {

    private val _signIn = MutableLiveData<SignInData>()
    val signIn: LiveData<SignInData>
        get() = _signIn


    // 토큰 재발급 및 자동 로그인
    fun postRenewalToken(refreshToken: String) {
        viewModelScope.launch {
            kotlin.runCatching { postRenewalTokenUseCase(refreshToken) }
                .onSuccess {
                    _signIn.value = it

                    Timber.d("auth: 서버 통신 성공")
                    FirebaseAnalyticsUtil.autoLogin()

                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("auth: 서버 통신 실패")
                }
        }
    }

}