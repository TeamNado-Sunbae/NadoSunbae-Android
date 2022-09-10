package com.nadosunbae_android.app.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.model.main.AppLinkData
import com.nadosunbae_android.domain.model.main.MajorSelectData
import com.nadosunbae_android.domain.model.major.MajorListData
import com.nadosunbae_android.domain.model.sign.SignInData
import com.nadosunbae_android.domain.repository.major.MajorRepository
import com.nadosunbae_android.domain.usecase.classroom.GetClassRoomMainDataUseCase
import com.nadosunbae_android.domain.usecase.classroom.GetSeniorDataUseCase
import com.nadosunbae_android.domain.usecase.main.GetAppLinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val majorRepository: MajorRepository,
    val getClassRoomMainDataUseCase: GetClassRoomMainDataUseCase,
    val getSeniorDataUseCase: GetSeniorDataUseCase,
    val getAppLinkUseCase: GetAppLinkUseCase
) : ViewModel(), LoadableViewModel {

    // 로그인 response 데이터
    private val _signData = MutableLiveData<SignInData.User>()
    val signData: LiveData<SignInData.User>
        get() = _signData


    //과방탭
    //과방탭에서 질문탭 및 정보탭 select 구분 (과방)
    var classRoomNum = MutableLiveData<Int>()

    //과방탭 프래그먼트 전환 (1 -> 과방 메인, 2 -> 전체에게 질문 3 -> 질문 구성원 목록 4 -> 선배 개인 페이지 5-> 학과 후기 6-> 마이페이지)
    var classRoomFragmentNum = MutableLiveData<Int>()

    //바텀 네비 아이템들 클릭된
    var bottomNavItem = MutableLiveData<Int>(0)

    //과방탭 뒤로가기 전환( 1 : 선배개인페이지 -> 구성원, 2: 구성원 -> 과방 메인)
    var classRoomBackFragmentNum = MutableLiveData<Int>()

    //과방탭 1:1 선배 Id
    var seniorId = MutableLiveData<Int>()

    //마이페이지탭 뒤로가기 전환
    var myPageFragmentNum = MutableLiveData<Int>()

    //닉네임 클릭시 Loading 부분
    var initLoading = MutableLiveData<Boolean>()

    //유저 아이디
    var userId = MutableLiveData<Int>()

    //유저 학교
    var univId = MutableLiveData<Int>()

    //과방탭 질문글 메인 조회
    private val _classRoomMain = MutableLiveData<List<ClassRoomData>>()
    val classRoomMain: LiveData<List<ClassRoomData>>
        get() = _classRoomMain

    //과방탭 정보글 등록된 정보글 없는 경우 (0 -> 없는 경우 1-> 있는 경우)
    var classRoomInfoEmpty = MutableLiveData<Int>(1)


    //차단 구분( 1일때 원글 또는 답글)
    var divisionBlock = MutableLiveData<Int>()

    //액티비티 종료
    var informationDetail = MutableLiveData<Int>()

    //학과 목록 id
    var majorId = MutableLiveData<Int>()


    // 선택 학과
    private var _selectedMajor = MutableLiveData<MajorSelectData>()
    val selectedMajor: LiveData<MajorSelectData>
        get() = _selectedMajor

    // 필터
    val filterData = MutableLiveData(FilterData(1, listOf(1, 2, 3, 4, 5)))

    // 구성원 전체보기
    private val _seniorData = MutableLiveData<ClassRoomSeniorData>()
    val seniorData: LiveData<ClassRoomSeniorData>
        get() = _seniorData

    // 본전공
    private val _firstMajor = MutableLiveData<MajorSelectData>()
    val firstMajor: LiveData<MajorSelectData>
        get() = _firstMajor

    // 제2전공
    private val _secondMajor = MutableLiveData<MajorSelectData>()
    val secondMajor: LiveData<MajorSelectData>
        get() = _secondMajor

    //앱링크 조회
    val appLink = MutableLiveData<AppLinkData>()


    //마이페이지
    //마이페이지 탭에서 질문탭 및 정보탭 select 구분
    var mypageNum = MutableLiveData<Int>()

    //마이페이지 프래그먼트 전환
    var mypageFragmentNum = MutableLiveData<Int>()


    //알림
    //알림 클릭 이벤트(1->후기, 2->과방, 3->알람, 4->마이페이지)
    //var notificationClickNum = MutableLiveData<Int>()

    //학교별 학과 리스트 데이터
    private var _majorList = MutableStateFlow(listOf(MajorListData.DEFAULT))
    val majorList: StateFlow<List<MajorListData>>
        get() = _majorList

    //과방 메인 데이터
    fun getClassRoomMain(postTypeId: Int, majorId: Int, sort: String = "recent") {
        viewModelScope.launch {
            runCatching { getClassRoomMainDataUseCase(postTypeId, majorId, sort) }
                .onSuccess {
                    _classRoomMain.value = it
                    Timber.d("classRoomMain: 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("classRoomMain: 서버 통신 실패")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    //TODO 회원가입시에 가져오기? 근데 회원가입 없는 기존에 사람들은? 이건 고민필요
    //학과 리스트 가져오기
    fun getMajorList(universityId: String, filter: String, exclude: String) {
        viewModelScope.launch {
            majorRepository.getMajorList(universityId, filter, exclude)
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("학과 리스트 가져오기 실패")
                }
                .collectLatest {
                    _majorList.value = it
                    Timber.d("학과 리스트 가져오기 성공 $it")
                }
        }
    }


    //과방 구성원 전체
    fun getClassRoomSenior(majorId: Int) {
        viewModelScope.launch {
            runCatching { getSeniorDataUseCase(majorId) }
                .onSuccess {
                    _seniorData.value = it
                    Timber.d("classRoomSenior: 구성원 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("classRoomSenior: 구성원 서버 통신 실패")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    //링크 조회
    fun getAppLink() {
        viewModelScope.launch {
            kotlin.runCatching { getAppLinkUseCase() }
                .onSuccess {
                    appLink.value = it
                    Timber.d("AppLink: 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("AppLink: 서버 통신 실패")
                }
        }
    }

    fun setSelectedMajor(majorData: MajorSelectData) {
        _selectedMajor.value = majorData

        // 선택한 학과 추적
        FirebaseAnalyticsUtil.setSelectedMajor(majorData.majorName)
    }

    fun setFirstMajor(majorData: MajorSelectData) {
        _firstMajor.value = majorData
    }

    fun setSecondMajor(majorData: MajorSelectData) {
        _secondMajor.value = majorData
    }

    fun setSignData(signData: SignInData.User) {
        _signData.value = signData
        userId.value = signData.userId
        univId.value = signData.universityId
    }

    fun clearFilter() {
        filterData.value = FilterData(1, listOf(1, 2, 3, 4, 5))
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

    override val onLoadingEnd = MutableLiveData<Boolean>()


}

