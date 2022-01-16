package com.nadosunbae_android.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepositoryImpl

class MainViewModel() : ViewModel() {
    val classRoomRepository : ClassRoomRepository = ClassRoomRepositoryImpl()


    //과방탭에서 질문탭 및 정보탭 select 구분 (과방)
    var classRoomNum = MutableLiveData<Int>()

    //과방탭 프래그먼트 전환 (1 -> 과방 메인, 2 -> 전체에게 질문 3 -> 질문 구성원 목록)
    var classRoomFragmentNum = MutableLiveData<Int>()

    // 선택 학과
    private var _selectedMajor = MutableLiveData<String>()
    val selectedMajor: LiveData<String>
        get() = _selectedMajor



    /*
        test data (api에서 불러오면 다 지울 예정)
     */
    fun setSelectedMajor(major: String) {
        _selectedMajor.value = major
    }
}