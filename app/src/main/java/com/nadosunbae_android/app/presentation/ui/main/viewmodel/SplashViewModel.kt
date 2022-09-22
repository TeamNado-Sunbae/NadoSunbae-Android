package com.nadosunbae_android.app.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.app.AppVersionData
import com.nadosunbae_android.domain.model.sign.SignInData
import com.nadosunbae_android.domain.repository.app.AppRepository
import com.nadosunbae_android.domain.usecase.sign.PostRenewalTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val postRenewalTokenUseCase: PostRenewalTokenUseCase,
    private val appRepository: AppRepository
) : ViewModel() {

    private val _signIn = MutableLiveData<SignInData>()
    val signIn: LiveData<SignInData>
        get() = _signIn

    //업데이트 확인
    val updateAvailability = MutableLiveData<Boolean>(false)

    //앱 버전 데이터
    private var _appVersion = MutableStateFlow(AppVersionData.DEFAULT)
    val appVersion : StateFlow<AppVersionData>
        get() = _appVersion

    // 토큰 재발급 및 자동 로그인
    fun postRenewalToken() {
        viewModelScope.launch {
            kotlin.runCatching { postRenewalTokenUseCase() }
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

    //앱 버전 가져오기
    fun getAppVersion(){
        viewModelScope.launch {
            appRepository.getAppVersion()
                .catch {
                    Timber.d("앱 버전 데이터 받아오기 실패")
                }
                .collectLatest {
                    _appVersion.value = it
                    Timber.d("앱 버전 데이터 가져오기 성공")
                }
        }
    }

}