package com.nadosunbae_andorid.presentation.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {



    //과방탭에서 질문탭 및 정보탭 구분 (과방)
    var classRoomNum = MutableLiveData<Int>()


}