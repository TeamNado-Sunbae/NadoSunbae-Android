package com.nadosunbae_andorid.presentation.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_andorid.data.repository.classroom.ClassRoomRepository
import com.nadosunbae_andorid.data.repository.classroom.ClassRoomRepositoryImpl

class MainViewModel() : ViewModel() {
    val classRoomRepository : ClassRoomRepository = ClassRoomRepositoryImpl()


    //과방탭에서 질문탭 및 정보탭 구분 (과방)
    var classRoomNum = MutableLiveData<Int>()




}