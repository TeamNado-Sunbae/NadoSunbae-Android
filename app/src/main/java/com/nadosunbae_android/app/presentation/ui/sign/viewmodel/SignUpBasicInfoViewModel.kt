package com.nadosunbae_android.app.presentation.ui.sign.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nadosunbae_android.app.util.ResultWrapper
import com.nadosunbae_android.app.util.safeApiCall
import com.nadosunbae_android.data.model.request.sign.RequestSignUp
import com.nadosunbae_android.domain.model.sign.*
import com.nadosunbae_android.domain.usecase.classroom.*
import com.nadosunbae_android.domain.usecase.sign.GetSecondDepartmentUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpBasicInfoViewModel(
    val getFirstDepartmentUseCase: GetFirstDepartmentUseCase,
    val getSecondDepartmentUseCase: GetSecondDepartmentUseCase,
    val postSignEmailUseCase: PostSignEmailUseCase,
    val postSignInUseCase: PostSignInUseCase,
    val postSignNicknameUseCase: PostSignNicknameUseCase,
    val postSignUpUseCase: PostSignUpUseCase

) : ViewModel() {
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
    var signIn: MutableLiveData<SignInItem> = MutableLiveData()

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
                    Log.d("NickNameDuplication", "네트워크 실패")
                    nicknameDuplicationCheck.value = NicknameDuplicationCheck(500, false)
                }
                is ResultWrapper.GenericError -> {
                    checkStatus(nicknameDuplication.code)
                    nicknameDuplicationCheck.value =
                        NicknameDuplicationCheck(nicknameDuplication.code!!, false)
                }
            }
            Log.d("nicknameDuplication", status.value.toString())
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
                    Log.d("EmailDuplication", "네트워크 실패")
                    emailDuplicationCheck.value = EmailDuplicationCheck(500, false)
                }
                is ResultWrapper.GenericError -> {
                    checkStatus(emailDuplication.code)
                    emailDuplicationCheck.value =
                        EmailDuplicationCheck(emailDuplication.code!!, false)
                }
            }
            Log.d("emailDuplication", status.value.toString())
        }
    }

    //로그인
    fun signIn(signInData: SignInData) {
        viewModelScope.launch {
            when (val postSignIn = safeApiCall(Dispatchers.IO) { postSignInUseCase(signInData) }) {
                is ResultWrapper.Success -> signIn.value = postSignIn.data!!
                is ResultWrapper.NetworkError -> {
                    Log.d("SignIn", "네트워크 실패")
                    signIn.value = SignInItem(500, false, "", SignInItem.User())
                }
                is ResultWrapper.GenericError -> {
                    checkStatus(postSignIn.code)
                    signIn.value = SignInItem(postSignIn.code!!, false, "", SignInItem.User())
                }
            }
            Log.d("signInStatus", status.value.toString())
        }
    }

    //회원가입
    fun signUp(signUpData: SignUpData) {
        CoroutineScope(Dispatchers.Main.immediate).launch {
            kotlin.runCatching { postSignUpUseCase(signUpData) }
                .onSuccess {
                    signUp.value = it
                    Log.d("SignUp", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("SignUp", "서버 통신 실패")
                }
        }
    }


    //본 전공 선택
    fun getFirstDepartment(universityId: Int, filter: String) {
        viewModelScope.launch {
            kotlin.runCatching { getFirstDepartmentUseCase(universityId, filter) }
                .onSuccess {
                    firstDepartment.value = it
                    Log.d("FirstMajorBottomSheet", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("FirstMajorBottomSheet", "서버 통신 실패")
                }
        }
    }

    // 제 2전공 선택
    fun getSecondDepartment(universityId: Int, filter: String) {
        viewModelScope.launch {
            kotlin.runCatching { getSecondDepartmentUseCase(universityId, filter) }
                .onSuccess {
                    secondDepartment.value = it
                    Log.d("SecondMajorBottomSheet", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("SecondMajorBottomSheet", "서버 통신 실패")
                }
        }
    }
}