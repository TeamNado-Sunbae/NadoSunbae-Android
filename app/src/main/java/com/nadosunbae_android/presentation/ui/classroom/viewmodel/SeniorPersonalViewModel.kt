package com.nadosunbae_android.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.response.classroom.ResponseSeniorPersonalData
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepositoryImpl

class SeniorPersonalViewModel : ViewModel() {
    private val classRoomRepository : ClassRoomRepository = ClassRoomRepositoryImpl()
    //선배 개인페이지
    private val _seniorPersonal = MutableLiveData<ResponseSeniorPersonalData>()
    val seniorPersonal : LiveData<ResponseSeniorPersonalData>
        get() = _seniorPersonal



    //선배 개인페이지 정보 서버통신
    fun getSeniorPersonal(userId : Int){
        classRoomRepository.getSeniorPersonal(userId,
        onResponse = {
            if(it.isSuccessful){
                _seniorPersonal.value = it.body()
                Log.d("seniorPersonal", "선배 개인페이지 서버 통신 완료")
            }
        },
            onFailure ={
                it.printStackTrace()
                Log.d("seniorPersonal", "선배 개인페이지 서버 통신 실패")
            }
            )
    }
}