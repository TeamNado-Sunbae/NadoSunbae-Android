package com.nadosunbae_android.app.presentation.ui.sign.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.app.util.ResultWrapper
import com.nadosunbae_android.app.util.safeApiCall
import com.nadosunbae_android.data.model.request.sign.RequestSignUp
import com.nadosunbae_android.domain.model.sign.*
import com.nadosunbae_android.domain.usecase.classroom.*
import com.nadosunbae_android.domain.usecase.sign.GetSecondDepartmentUseCase
import com.nadosunbae_android.domain.usecase.sign.PostCertificationEmailUseCase
import com.nadosunbae_android.domain.usecase.sign.PostRenewalTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpBasicInfoViewModel @Inject constructor(
    private val getFirstDepartmentUseCase: GetFirstDepartmentUseCase,
    private val getSecondDepartmentUseCase: GetSecondDepartmentUseCase,
    private val postSignEmailUseCase: PostSignEmailUseCase,
    private val postSignInUseCase: PostSignInUseCase,
    private val postSignNicknameUseCase: PostSignNicknameUseCase,
    private val postSignUpUseCase: PostSignUpUseCase,
    private val postCertificationEmailUseCase: PostCertificationEmailUseCase
) : ViewModel() {

    val onLoadingEnd = MutableLiveData<Boolean>(false)

    //닉네임 중복 체크 변수
    var nicknameDuplicationCheck: MutableLiveData<NicknameDuplicationCheck> = MutableLiveData()

    //이메일 중복 체크 변수
    var emailDuplicationCheck: MutableLiveData<EmailDuplicationCheck> = MutableLiveData()

    //회원가입 request
    val requestSignUp = RequestSignUp("", "", "", 0, 0, "", 0, "")

    //로그인시 필요한 값 -> email, password, deviceToken
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var deviceToken = MutableLiveData<String>()

    //로그인
    var signIn: MutableLiveData<SignInData> = MutableLiveData()

    //로그인 status 체크
    var signInStatus = MutableLiveData<Int>()

    //약관 동의 상태
    var isAgreementChecked = MutableLiveData<Boolean>()

    // 학과 정보에서 작성하는 정보
    var univId = MutableLiveData<Int>()
    var firstMajorId = MutableLiveData<Int>()
    var firstMajorName = MutableLiveData<String>()
    var firstMajorStart = MutableLiveData<String>()
    var secondMajorId = MutableLiveData<Int>()
    var secondMajorName = MutableLiveData<String>()
    var secondMajorStart = MutableLiveData<String>()

    //로그인 상태
    private var _status = MutableLiveData<Int?>()
    val status: LiveData<Int?> = _status

    //회원가입
    val signUp: MutableLiveData<SignUpItem> = MutableLiveData()

    //닉네임
    var nickName = MutableLiveData<String>()

    //제 1전공
    val firstDepartment = MutableLiveData<SignBottomSheetItem>()

    //제 2전공
    val secondDepartment = MutableLiveData<SignBottomSheetItem>()

    var firstDepartmentClick = MutableLiveData<Boolean>(false)
    var firstDepartmentGo = MutableLiveData<Boolean>(false)
    var secondDepartmentClick = MutableLiveData<Boolean>(false)
    var secondDepartmentGo = MutableLiveData<Boolean>(false)

    val certificationEmail = MutableLiveData<CertificationEmailItem>()

    //미진입 없을 때
    var selectedAll = MediatorLiveData<Boolean>().apply {
        this.addSource(firstDepartmentClick) {
            this.value = isCompleteBtn()
        }
        this.addSource(firstDepartmentGo) {
            this.value = isCompleteBtn()
        }
        this.addSource(secondDepartmentClick) {
            this.value = isCompleteBtn()
        }
        this.addSource(secondDepartmentGo) {
            this.value = isCompleteBtn()
        }
    }

    //회원가입 분기 처리
    private fun isCompleteBtn(): Boolean {
        return (firstDepartmentClick.value == true) && (firstDepartmentGo.value == true)
                && (secondDepartmentClick.value == true) && (secondDepartmentGo.value == true)
    }


    //로그인 상태 체크
    fun checkStatus(status: Int?) {
        _status.value = status
    }

    //닉네임 중복 체크
    fun nickNameDuplication(nicknameDuplicationData: NicknameDuplicationData) {
        viewModelScope.launch {
            when (val nicknameDuplication =
                safeApiCall(Dispatchers.IO) { postSignNicknameUseCase(nicknameDuplicationData) }) {
                is ResultWrapper.Success -> nicknameDuplicationCheck.value =
                    NicknameDuplicationCheck(200, true)
                is ResultWrapper.NetworkError -> {
                    Timber.d("NickNameDuplication : 네트워크 실패")
                    nicknameDuplicationCheck.value = NicknameDuplicationCheck(500, false)
                }
                is ResultWrapper.GenericError -> {
                    checkStatus(nicknameDuplication.code)
                    nicknameDuplicationCheck.value =
                        NicknameDuplicationCheck(nicknameDuplication.code!!, false)
                }
            }
            Timber.d("nicknameDuplication: ${status.value.toString()}")
        }
    }


    //이메일 중복 체크
    fun emailDuplication(emailDuplicationData: EmailDuplicationData) {
        viewModelScope.launch {
            when (val emailDuplication =
                safeApiCall(Dispatchers.IO) { postSignEmailUseCase(emailDuplicationData) }) {
                is ResultWrapper.Success -> emailDuplicationCheck.value =
                    EmailDuplicationCheck(200, true)
                is ResultWrapper.NetworkError -> {
                    Timber.d("EmailDuplication : 네트워크 실패")
                    emailDuplicationCheck.value = EmailDuplicationCheck(500, false)
                }
                is ResultWrapper.GenericError -> {
                    checkStatus(emailDuplication.code)
                    emailDuplicationCheck.value =
                        EmailDuplicationCheck(emailDuplication.code!!, false)
                }
            }
            Timber.d("emailDuplication: ${status.value.toString()}")
        }
    }


    //로그인
    fun signIn(signInItem: SignInItem) {
        viewModelScope.launch {
            when (val postSignIn = safeApiCall(Dispatchers.IO) { postSignInUseCase(signInItem) }) {
                is ResultWrapper.Success -> {
                    signIn.value = postSignIn.data!!
                    signInStatus.value = 200
                    Timber.d("testaaa : ${signIn.value.toString()}")

                    FirebaseAnalyticsUtil.login()

                }
                is ResultWrapper.NetworkError -> {
                    Timber.d("SignIn : 네트워크 실패")
                    signInStatus.value = 500
                }
                is ResultWrapper.GenericError -> {
                    //checkStatus(postSignIn.code)
                    signInStatus.value = postSignIn.code ?: 202
                }
            }
                .also {
                    onLoadingEnd.value = true
                }
            Timber.d("signInStatus: ${signInStatus.value.toString()}")
        }
    }

    //회원가입
    fun signUp(signUpData: SignUpData) {
        CoroutineScope(Dispatchers.Main.immediate).launch {
            kotlin.runCatching { postSignUpUseCase(signUpData) }
                .onSuccess {
                    signUp.value = it
                    Timber.d("SignUp : 서버 통신 성공")

                    FirebaseAnalyticsUtil.signup()

                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("SignUp : 서버 통신 실패")
                }
        }
    }

    //이메일 재전송
    fun postCertificationEmail(certificationEmailData: CertificationEmailData) {
        viewModelScope.launch {
            kotlin.runCatching { postCertificationEmailUseCase(certificationEmailData) }
                .onSuccess {
                    certificationEmail.value = it
                    Timber.d("EmailCertification :서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("EmailCertification : 서버 통신 실패")
                }
        }
    }

    //본 전공 선택
    fun getFirstDepartment(universityId: Int, filter: String) {
        viewModelScope.launch {
            kotlin.runCatching { getFirstDepartmentUseCase(universityId, filter) }
                .onSuccess {
                    firstDepartment.value = it
                    Timber.d("FirstMajorBottomSheet : 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("FirstMajorBottomSheet : 서버 통신 실패")
                }
        }
    }

    // 제 2전공 선택
    fun getSecondDepartment(universityId: Int, filter: String) {
        viewModelScope.launch {
            kotlin.runCatching { getSecondDepartmentUseCase(universityId, filter) }
                .onSuccess {
                    secondDepartment.value = it
                    Timber.d("SecondMajorBottomSheet : 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("SecondMajorBottomSheet : 서버 통신 실패")
                }
        }
    }

}