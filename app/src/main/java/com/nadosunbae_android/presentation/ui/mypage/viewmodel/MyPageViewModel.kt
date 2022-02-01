package com.nadosunbae_android.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.repository.mypage.MyPageRepository
import com.nadosunbae_android.repositoryimpl.mypage.MyPageRepository
import com.nadosunbae_android.repositoryimpl.mypage.MyPageRepositoryImpl

class MyPageViewModel : ViewModel() {
    val myPageRepository: MyPageRepository = MyPageRepositoryImpl()
    val personalQuestion = MutableLiveData<ResponseMypageQuestionData>()
    val personalInfo = MutableLiveData<ResponseMypageMyInfo>()

    private val _myPagePersonal = MutableLiveData<ResponseMypageMyInfo>()
    val myPagePersonal : LiveData<ResponseMypageMyInfo>
    get() = _myPagePersonal

    //마이페이지 1:1 질문 구분 변수




    /*
    //마이페이지 1:1 질문 메인 조회
    private val _myPageMain = MutableLiveData<ResponseMypageQuestionData>()
    val myPageMain: LiveData<ResponseMypageQuestionData>
        get() = _myPageMain

     */

    //마이페이지 개인 정보 조회
    /*
    private val _myPagePersonal = MutableLiveData<ResponseMypageMyInfo>()
    val myPagePersonal : LiveData<ResponseMypageMyInfo>
        get() = _myPagePersonal

     */

    //마이페이지 1:1 질문
    fun getMyPageQuestion(userId: Int, sort: String = "recent") {
        myPageRepository.getMyPageQuestion(userId, sort,
            onResponse = {
                if (it.isSuccessful) {
                    personalQuestion.value = it.body()
                    Log.d("MyPageQuestion", "서버 통신 성공")
                }},
            onFailure= {
                it.printStackTrace()
                Log.d("MyPageQuestion", "서버 통신 실패")
            }
        )
    }

    //마이페이지 개인 정보 서버통신
    fun getPersonalInfo(){
        myPageRepository.getMyPageMyInfo(
            onResponse = {
                if(it.isSuccessful){
                    personalInfo.value = it.body()
                    Log.d("myPageInfo", "서버 통신 완료")
                }
            },
            onFailure ={
                it.printStackTrace()
                Log.d("myPageInfo", "서버 통신 실패")
            }
        )
    }


}

