package com.nadosunbae_andorid.presentation.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import com.nadosunbae_andorid.data.repository.classroom.ClassRoomRepository
import com.nadosunbae_andorid.data.repository.classroom.ClassRoomRepositoryImpl

class MainViewModel() : ViewModel() {
    val classRoomRepository : ClassRoomRepository = ClassRoomRepositoryImpl()


    //과방탭에서 질문탭 및 정보탭 구분 (과방)
    var classRoomNum = MutableLiveData<Int>()


    //서버통신 예시
    val classRoomResponse = MutableLiveData<ResponseClassRoomData>()

    fun getRoomData(userId: String, userInfo: String){
        classRoomRepository.getClassRoom(userId, userInfo,
            onResponse = {
                if(it.isSuccessful){
                    classRoomResponse.value = it.body()
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d("server", "서버 통신 실패")
            }
            )
    }

}