package com.nadosunbae_andorid.presentation.ui.classroom.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import com.nadosunbae_andorid.data.repository.classroom.ClassRoomRepositoryImpl
import retrofit2.Callback

class classRoomViewModel : ViewModel() {

    val classRoomRepository = ClassRoomRepositoryImpl()


    var classRoomData = MutableLiveData<ResponseClassRoomData>()





  
}