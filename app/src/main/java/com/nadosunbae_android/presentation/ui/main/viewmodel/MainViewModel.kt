package com.nadosunbae_android.presentation.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomSeniorData
import com.nadosunbae_android.data.model.response.main.ResponseMajorListData
import com.nadosunbae_android.data.model.ui.MajorData
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepositoryImpl
import com.nadosunbae_android.data.repository.main.MainRepository
import com.nadosunbae_android.data.repository.main.MainRepositoryImpl
import com.nadosunbae_android.data.repository.mypage.MyPageRepositoryImpl

class MainViewModel() : ViewModel() {
    val mainRepository: MainRepository = MainRepositoryImpl()
    val classRoomRepository: ClassRoomRepository = ClassRoomRepositoryImpl()
    val mypageRepository: MyPageRepositoryImpl = MyPageRepositoryImpl()

    //과방탭
    //과방탭에서 질문탭 및 정보탭 select 구분 (과방)
    var classRoomNum = MutableLiveData<Int>()

    //과방탭 프래그먼트 전환 (1 -> 과방 메인, 2 -> 전체에게 질문 3 -> 질문 구성원 목록 4 -> 선배 개인 페이지 5-> 학과 후기)
    var classRoomFragmentNum = MutableLiveData<Int>()

    //과방탭 뒤로가기 전환( 1 : 선배개인페이지 -> 구성원, 2: 구성원 -> 과방 메인)
    var classRoomBackFragmentNum = MutableLiveData<Int>()

    //과방탭 1:1 선배 Id
    var seniorId  = MutableLiveData<Int>()

    //과방탭 질문글 메인 조회
    private val _classRoomMain = MutableLiveData<ResponseClassRoomMainData>()
    val classRoomMain : LiveData<ResponseClassRoomMainData>
        get() = _classRoomMain


    // 학과 목록
    private val _majorList = MutableLiveData<ResponseMajorListData>()
    val majorList: LiveData<ResponseMajorListData>
        get() = _majorList

    // 선택 학과
    private var _selectedMajor = MutableLiveData<MajorData>()
    val selectedMajor: LiveData<MajorData>
        get() = _selectedMajor

    // 구성원 전체보기
    private val _seniorData = MutableLiveData<ResponseClassRoomSeniorData.Data>()
    val seniorData : LiveData<ResponseClassRoomSeniorData.Data>
        get() = _seniorData

    // 본전공
    private val _firstMajor = MutableLiveData<MajorData>()
    val firstMajor: LiveData<MajorData>
        get() = _firstMajor

    // 제2전공
    private val _secondMajor = MutableLiveData<MajorData>()
    val secondMajor: LiveData<MajorData>
        get() = _secondMajor


    //마이페이지
    //마이페이지 탭에서 질문탭 및 정보탭 select 구분
    var mypageNum = MutableLiveData<Int>()

    //마이페이지 프래그먼트 전환
    var mypageFragmentNum = MutableLiveData<Int>()


    // 학과 목록 데이터
    fun getMajorList(universityId: Int, filter: String = "all") {
        mainRepository.getMajorList(universityId, filter,
            onResponse = {
                _majorList.value = it.body()
                Log.d("MainRepository", "서버 통신 성공")
            },
            onFailure = {
                it.printStackTrace()
                Log.d("MainRepository", "서버 통신 실패")
            }
        )
    }



    //과방 메인 데이터
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

    //과방 구성원 전체
    fun getClassRoomSenior(majorId : Int){
        classRoomRepository.getClassRoomSenior(majorId,
            onResponse = {
                if(it.isSuccessful){

                _seniorData.value = it.body()?.data
                Log.d("classRoomSenior", "구성원 서버 통신 성공")
            }},
            onFailure = {
                it.printStackTrace()
                Log.d("classRoomSenior", "구성원 서버 통신 실패")
            })
    }


    fun setSelectedMajor(majorData: MajorData) {
        _selectedMajor.value = majorData
    }

    fun setFirstMajor(majorData: MajorData) {
        _firstMajor.value = majorData
    }

    fun setSecondMajor(majorData: MajorData) {
        _secondMajor.value = majorData
    }

}