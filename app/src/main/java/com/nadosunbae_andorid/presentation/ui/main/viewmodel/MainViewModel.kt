package com.nadosunbae_andorid.presentation.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_andorid.data.repository.classroom.ClassRoomRepository
import com.nadosunbae_andorid.data.repository.classroom.ClassRoomRepositoryImpl

class MainViewModel() : ViewModel() {
    val classRoomRepository : ClassRoomRepository = ClassRoomRepositoryImpl()


    //과방탭에서 질문탭 및 정보탭 select 구분 (과방)
    var classRoomNum = MutableLiveData<Int>()

    //과방탭 프래그먼트 전환 (1 -> 과방 메인, 2 -> 전체에게 질문)
    var classRoomFragmentNum = MutableLiveData<Int>()

    // 선택 학과
    private var _selectedMajor = MutableLiveData<String>()
    val selectedMajor: LiveData<String>
        get() = _selectedMajor

    // 선택 학과홈페이지 링크
    private var _urlHomepage = MutableLiveData<String>()
    val urlHomepage: LiveData<String>
        get() = _urlHomepage

    // 선택 학과 이수과목 일람표 링크
    private var _urlSubjectTable = MutableLiveData<String>()
    val urlSubjectTable: LiveData<String>
        get() = _urlSubjectTable


}