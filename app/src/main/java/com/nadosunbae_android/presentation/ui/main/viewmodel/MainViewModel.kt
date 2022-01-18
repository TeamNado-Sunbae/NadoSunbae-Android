package com.nadosunbae_android.presentation.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.repository.mypage.MyPageRepositoryImpl
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepositoryImpl
import org.koin.core.time.measureDurationForResult

class MainViewModel() : ViewModel() {
    val classRoomRepository: ClassRoomRepository = ClassRoomRepositoryImpl()
    val mypageRepository: MyPageRepositoryImpl = MyPageRepositoryImpl()
    //과방탭
    //과방탭에서 질문탭 및 정보탭 select 구분 (과방)
    var classRoomNum = MutableLiveData<Int>()

    //과방탭 프래그먼트 전환 (1 -> 과방 메인, 2 -> 전체에게 질문 3 -> 질문 구성원 목록 4 -> 선배 개인 페이지 5-> 학과 후기)
    var classRoomFragmentNum = MutableLiveData<Int>()

    //과방탭 질문글 메인 조회
    private val _classRoomMain = MutableLiveData<ResponseClassRoomMainData>()
    val classRoomMain : LiveData<ResponseClassRoomMainData>
        get() = _classRoomMain


    // 선택 학과
    private var _selectedMajor = MutableLiveData<String>()
    val selectedMajor: LiveData<String>
        get() = _selectedMajor


    //마이페이지
    //마이페이지 탭에서 질문탭 및 정보탭 select 구분
    var mypageNum = MutableLiveData<Int>()

    //마이페이지 프래그먼트 전환
    var mypageFragmentNum = MutableLiveData<Int>()


    /*
        test data (api에서 불러오면 다 지울 예정)
     */
    fun setSelectedMajor(major: String) {
        _selectedMajor.value = major
    }




    fun getClassRoomMain(postTypeId : Int, majorId : Int, sort : String = "recent"){
        classRoomRepository.getClassRoomMain(postTypeId, majorId, sort,
            onResponse = {
                if(it.isSuccessful){
                    _classRoomMain.value = it.body()
                    Log.d("classRoomMain", "메인 서버 통신 성공")
                }},
                onFailure = {
                    it.printStackTrace()
                    Log.d("classRoomMain", "메인 서버 통신 실패")
            }
        )

    }
}