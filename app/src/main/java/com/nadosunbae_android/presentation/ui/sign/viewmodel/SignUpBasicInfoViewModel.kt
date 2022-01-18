package com.nadosunbae_android.presentation.ui.sign.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.response.sign.ResponseSignNickname
import com.nadosunbae_android.data.repository.sign.SignRepository
import com.nadosunbae_android.data.repository.sign.SignRepositoryImpl

class SignUpBasicInfoViewModel : ViewModel() {
    val signRepository : SignRepository = SignRepositoryImpl()
    //닉네임 중복 체크 변수
    private val _nickNameDuplication = MutableLiveData<ResponseSignNickname>()
    private val nickNameDuplication : LiveData<ResponseSignNickname>
        get() = _nickNameDuplication






    //닉네임 중복 체크
    fun nickNameDuplication(requestSignNickname: String){
        signRepository.postSignNickname(requestSignNickname,
            onResponse = {
                if(it.isSuccessful){
                    _nickNameDuplication.value = it.body()
                    Log.d("nickNameDuplication", "서버 통신 성공")
                }}
        ) {
            it.printStackTrace()
            Log.d("nickNameDuplication", "서버 통신 실패")
        }
    }
}