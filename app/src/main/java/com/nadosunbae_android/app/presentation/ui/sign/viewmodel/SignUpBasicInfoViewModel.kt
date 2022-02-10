package com.nadosunbae_android.app.presentation.ui.sign.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.data.model.request.sign.RequestSignUp
import com.nadosunbae_android.domain.model.sign.*
import com.nadosunbae_android.domain.usecase.classroom.*
import com.nadosunbae_android.domain.usecase.sign.GetSecondDepartmentUseCase
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
    var nickNameDuplication = MutableLiveData<NicknameDuplicationCheck>()

    //이메일 중복 체크 변수
    var emailDuplication = MutableLiveData<Boolean>()

    //회원가입 request
    val requestSignUp = RequestSignUp("", "", "", 0, 0, "", 0, "")

    //로그인시 필요한 값 -> email, password, deviceToken
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var deviceToken = MutableLiveData<String>()

    //로그인
    val signIn: MutableLiveData<SignInItem> = MutableLiveData()

    //회원가입
    val signUp: MutableLiveData<SignUpItem> = MutableLiveData()

    //닉네임
    var nickName = MutableLiveData<String>()

    //제 1전공
    val firstDepartment = MutableLiveData<SignBottomSheetItem>()

    //제 2전공
    val secondDepartment = MutableLiveData<SignBottomSheetItem>()


    //닉네임 중복 체크
    fun nickNameDuplication(nicknameDuplicationData: NicknameDuplicationData) {
        viewModelScope.launch {
            kotlin.runCatching { postSignNicknameUseCase(nicknameDuplicationData) }
                .onSuccess {
                    nickNameDuplication.value = it
                    Log.d("nickNameDuplication", "서버 통신 성공")

                }
                .onFailure {
                    when (nickNameDuplication.value?.status) {
                        409 -> Log.d("NickNameDuplication", "중복된 닉네임")
                        400 -> Log.d("NickNameDuplication", "필요한 값이 없음")
                        500 -> Log.d("NickNameDuplication", "서버 내 오류")
                    }
                }
        }
    }


    //이메일 중복 체크
    fun emailDuplication(emailDuplicationData: EmailDuplicationData) {
        viewModelScope.launch {
            kotlin.runCatching { postSignEmailUseCase(emailDuplicationData) }
                .onSuccess {
                    emailDuplication.value = it.success
                    Log.d("emailDuplication", "서버 통신 성공")

                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("emailDuplication", "서버 통신 실패")
                }
        }
    }

    //로그인
    fun signIn(signInData: SignInData) {
        viewModelScope.launch {
            kotlin.runCatching { postSignInUseCase(signInData) }
                .onSuccess {
                    signIn.value = it
                    Log.d("SignIn", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("SignIn", "서버 통신 실패")
                }
        }
    }

    //회원가입
    fun signUp(signUpData: SignUpData) {
        viewModelScope.launch {
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
                    firstDepartment.value = it
                    Log.d("FirstMajorBottomSheet", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("FirstMajorBottomSheet", "서버 통신 실패")
                }
        }
    }
}