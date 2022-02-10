package com.nadosunbae_android.app.presentation.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.model.main.MajorData
import com.nadosunbae_android.data.model.response.sign.ResponseSignIn
import com.nadosunbae_android.data.model.ui.MajorKeyData
import com.nadosunbae_android.domain.usecase.classroom.GetClassRoomMainDataUseCase
import com.nadosunbae_android.domain.usecase.classroom.GetSeniorDataUseCase
import com.nadosunbae_android.domain.usecase.main.GetMajorListDataUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    val getClassRoomMainDataUseCase : GetClassRoomMainDataUseCase,
    val getSeniorDataUseCase : GetSeniorDataUseCase,
    val getMajorListDataUseCase: GetMajorListDataUseCase
) : ViewModel() {

    // 로그인 response 데이터
    private val _signData = MutableLiveData<ResponseSignIn.Data.User>()
    val signData: LiveData<ResponseSignIn.Data.User>
        get() = _signData

    //과방탭
    //과방탭에서 질문탭 및 정보탭 select 구분 (과방)
    var classRoomNum = MutableLiveData<Int>()

    //과방탭 프래그먼트 전환 (1 -> 과방 메인, 2 -> 전체에게 질문 3 -> 질문 구성원 목록 4 -> 선배 개인 페이지 5-> 학과 후기 6-> 마이페이지)
    var classRoomFragmentNum = MutableLiveData<Int>()

    var myId = MutableLiveData<Int>()

    //과방탭 뒤로가기 전환( 1 : 선배개인페이지 -> 구성원, 2: 구성원 -> 과방 메인)
    var classRoomBackFragmentNum = MutableLiveData<Int>()

    //과방탭 1:1 선배 Id
    var seniorId  = MutableLiveData<Int>()

    //유저 아이디
    var userId = MutableLiveData<Int>()

    //과방탭 질문글 메인 조회
    private val _classRoomMain = MutableLiveData<List<ClassRoomData>>()
    val classRoomMain : LiveData<List<ClassRoomData>>
        get() = _classRoomMain


    // 학과 목록
    private val _majorList = MutableLiveData<List<MajorData>>()
    val majorList: LiveData<List<MajorData>>
        get() = _majorList


    //학과 목록 id
    var majorId = MutableLiveData<Int>()


    // 선택 학과
    private var _selectedMajor = MutableLiveData<MajorKeyData>()
    val selectedMajor: LiveData<MajorKeyData>
        get() = _selectedMajor

    // 필터
    val filterData = MutableLiveData<FilterData>(FilterData(1, listOf(1, 2, 3, 4, 5)))

    // 구성원 전체보기
    private val _seniorData = MutableLiveData<ClassRoomSeniorData>()
    val seniorData : LiveData<ClassRoomSeniorData>
        get() = _seniorData

    // 본전공
    private val _firstMajor = MutableLiveData<MajorKeyData>()
    val firstMajor: LiveData<MajorKeyData>
        get() = _firstMajor

    // 제2전공
    private val _secondMajor = MutableLiveData<MajorKeyData>()
    val secondMajor: LiveData<MajorKeyData>
        get() = _secondMajor


    //마이페이지
    //마이페이지 탭에서 질문탭 및 정보탭 select 구분
    var mypageNum = MutableLiveData<Int>()

    //마이페이지 프래그먼트 전환
    var mypageFragmentNum = MutableLiveData<Int>()


    //알림
    //알림 클릭 이벤트(1->후기, 2->과방, 3->알람, 4->마이페이지)
    //var notificationClickNum = MutableLiveData<Int>()


    // 학과 목록 데이터
    fun getMajorList(universityId: Int, filter: String = "all") {
        viewModelScope.launch {
            runCatching { getMajorListDataUseCase(universityId, filter) }
                .onSuccess {
                    _majorList.value = it
                    Log.d("MainRepository", "서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("MainRepository", "서버통신 실패")
                }
        }
    }


    //과방 메인 데이터
    fun getClassRoomMain(postTypeId : Int, majorId : Int, sort : String = "recent"){
       viewModelScope.launch {
           runCatching { getClassRoomMainDataUseCase(postTypeId, majorId, sort) }
               .onSuccess {
                   _classRoomMain.value = it
                   Log.d("classRoomMain", "서버 통신 성공")
               }
               .onFailure {
                   it.printStackTrace()
                   Log.d("classRoomMain", "서버 통신 실패")
               }
       }
    }

    //과방 구성원 전체
    fun getClassRoomSenior(majorId : Int){
        viewModelScope.launch {
            runCatching { getSeniorDataUseCase(majorId) }
                .onSuccess {
                    _seniorData.value = it
                    Log.d("classRoomSenior", "구성원 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("classRoomSenior", "구성원 서버 통신 실패")
                }
        }
    }


    fun setSelectedMajor(majorData: MajorKeyData) {
        _selectedMajor.value = majorData
    }

    fun setFirstMajor(majorData: MajorKeyData) {
        _firstMajor.value = majorData
    }

    fun setSecondMajor(majorData: MajorKeyData) {
        _secondMajor.value = majorData
    }

    fun setSignData(signData: ResponseSignIn.Data.User) {
        _signData.value = signData
        userId.value = signData.userId
    }

    companion object {
        const val FILTER_ALL = 1
        const val FILTER_FIRST_MAJOR = 2
        const val FILTER_SECOND_MAJOR = 3
    }

    data class FilterData(
        val writerFilter: Int,
        val tagFilter: List<Int>
    )

}