package com.nadosunbae_android.presentation.ui.sign.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.request.sign.RequestSignEmail
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.data.model.response.sign.ResponseFirstDepartment
import com.nadosunbae_android.data.model.response.sign.ResponseSignNickname
import com.nadosunbae_android.data.repository.sign.SignRepository
import com.nadosunbae_android.data.repository.sign.SignRepositoryImpl

class SignUpBasicInfoViewModel : ViewModel() {
    val signRepository: SignRepository = SignRepositoryImpl()

    //닉네임 중복 체크 변수
    var nickNameDuplication = MutableLiveData<Boolean>()

    //이메일 중복 체크 변수
    var emailDuplication = MutableLiveData<Boolean>()

    //제 1전공
    val firstDepartment = MutableLiveData<ResponseFirstDepartment>()
    val secondDepartment = MutableLiveData<ResponseFirstDepartment>()


    //닉네임
    var nickName = MutableLiveData<String>()

    //이메일
    var email = MutableLiveData<String>()


    //닉네임 중복 체크
    fun nickNameDuplication(requestSignNickname: RequestSignNickname) {
        signRepository.postSignNickname(requestSignNickname,
            onResponse = {
                if (it.isSuccessful) {
                    nickNameDuplication.value = it.body()?.success
                    Log.d("nickNameDuplication", "서버 통신 성공")
                } else {
                    nickNameDuplication.value = false
                    Log.d("nickNameDuplication", "중복된 아이디")
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d("nickNameDuplication", "서버 통신 실패")
            })
    }


    //이메일 중복 체크
    fun emailDuplication(requestSignEmail: RequestSignEmail) {
        signRepository.postSignEmail(requestSignEmail,
            onResponse = {
                if (it.isSuccessful) {
                    emailDuplication.value = it.body()?.success
                    Log.d("emailDuplication", "서버 통신 성공")
                } else {
                    emailDuplication.value = false
                    Log.d("emailDuplication", "중복된 이메일")
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d("emailDuplication", "서버 통신 실패")
            })
    }

    //본 전공 선택
    fun getFirstDepartment(universityId: Int, filter: String) {
        signRepository.getFirstDepartment(universityId, filter, {
            //onResponse
            if (it.isSuccessful) {
                firstDepartment.value = it.body()
                Log.d("firstDepartment", "서버 통신 성공")
            }
        }) {
            //onFailure
            it.printStackTrace()
            Log.d("firstDepartment", "서버 통신 실패")
        }
    }

    // 제 2전공 선택
    fun getSecondDepartment(universityId: Int, filter: String) {
        signRepository.getFirstDepartment(universityId, filter, {
            //onResponse
            if (it.isSuccessful) {
                secondDepartment.value = it.body()
                Log.d("secondDepartment", "서버 통신 성공")
            }
        }) {
            //onFailure
            it.printStackTrace()
            Log.d("secondDepartment", "서버 통신 실패")
        }
    }
}