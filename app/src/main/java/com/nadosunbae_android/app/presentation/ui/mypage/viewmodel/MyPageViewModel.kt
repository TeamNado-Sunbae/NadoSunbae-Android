package com.nadosunbae_android.app.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.domain.model.mypage.MyPageMyInfo
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.domain.usecase.mypage.GetMyPageMyInfoUseCase
import com.nadosunbae_android.domain.usecase.mypage.GetMyPageQuestionUseCase
import kotlinx.coroutines.launch

class MyPageViewModel(
    val getMyPageMyInfoUseCase: GetMyPageMyInfoUseCase,
    val getMyPageQuestionUseCase: GetMyPageQuestionUseCase

    ) : ViewModel() {
    val personalQuestion = MutableLiveData<MyPageQuestionData>()
    val personalInfo = MutableLiveData<MyPageMyInfo>()

    private val _myPagePersonal = MutableLiveData<ResponseMypageMyInfo>()
    val myPagePersonal : LiveData<ResponseMypageMyInfo>
    get() = _myPagePersonal

    //마이페이지 1:1 질문
    fun getMyPageQuestion(userId: Int, sort: String = "recent") {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageQuestionUseCase(userId, sort) }
                .onSuccess {
                    personalQuestion.value = it
                    Log.d("nickNameDuplication", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("nickNameDuplication", "서버 통신 실패")
                }

        }
    }

    //마이페이지 개인 정보 서버통신
    fun getPersonalInfo(){
        viewModelScope.launch {
            kotlin.runCatching { getMyPageMyInfoUseCase() }
                .onSuccess {
                    personalInfo.value = it
                    Log.d("myPageInfo", "서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("nickNameDuplication", "서버 통신 실패")
                }
        }
    }
}

