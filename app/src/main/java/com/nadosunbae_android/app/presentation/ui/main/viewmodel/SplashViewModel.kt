package com.nadosunbae_android.app.presentation.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.domain.model.sign.SignInData
import com.nadosunbae_android.domain.usecase.sign.PostRenewalTokenUseCase
import kotlinx.coroutines.launch

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
                    Log.d("auth", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("auth", "서버 통신 실패")
                }
        }
    }

}