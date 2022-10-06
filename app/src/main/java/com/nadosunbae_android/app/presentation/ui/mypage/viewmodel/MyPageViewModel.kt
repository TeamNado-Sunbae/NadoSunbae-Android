package com.nadosunbae_android.app.presentation.ui.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.util.ResultWrapper
import com.nadosunbae_android.app.util.safeApiCall
import com.nadosunbae_android.domain.model.favorites.FavoritesData
import com.nadosunbae_android.domain.model.favorites.FavoritesParam
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.major.MajorListData
import com.nadosunbae_android.domain.model.mypage.*
import com.nadosunbae_android.domain.model.sign.SignInData
import com.nadosunbae_android.domain.model.user.*
import com.nadosunbae_android.domain.repository.favorites.FavoritesRepository
import com.nadosunbae_android.domain.repository.major.MajorRepository
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import com.nadosunbae_android.domain.repository.user.UserRepository
import com.nadosunbae_android.domain.usecase.review.GetMajorInfoDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val getMajorInfoDataUseCase: GetMajorInfoDataUseCase,
    private val userRepository: UserRepository,
    private val favoritesRepository: FavoritesRepository,
    private val majorRepository: MajorRepository,
    private val myPageRepository: MyPageRepository

) : ViewModel(), LoadableViewModel {

    //커뮤니티 학과 즐겨찾기
    private var _communityFavorites = MutableStateFlow(FavoritesData.DEFAULT)
    val communityFavorites: StateFlow<FavoritesData>
        get() = _communityFavorites

    //학과 변경 리스트
    private var _majorList = MutableLiveData<List<MajorListData>>()
    val majorList: LiveData<List<MajorListData>>
        get() = _majorList

    fun setMajorList(data: List<MajorListData>) {
        _majorList.value = data
    }

    //학과 선택 내용
    private var _firstFilter = MutableStateFlow(SelectableData.DEFAULT)
    val firstFilter: StateFlow<SelectableData>
        get() = _firstFilter

    fun setFilter(filter: SelectableData) {
        _firstFilter.value = filter
    }

    //학과 선택 내용
    private var _secondFilter = MutableStateFlow(SelectableData.DEFAULT)
    val secondFilter: StateFlow<SelectableData>
        get() = _secondFilter

    fun setSecondFilter(filter: SelectableData) {
        _secondFilter.value = filter
    }


    // 로그인 response 데이터
    private val _signData = MutableLiveData<SignInData.User>()
    val signData: LiveData<SignInData.User>
        get() = _signData

    override val onLoadingEnd = MutableLiveData<Boolean>(false)

    //이미지 수정 int
    var selectImgId = MutableLiveData<Int>()

    //최종 선택한 이미지 int
    //var changedImgId = MutableLiveData<Int>()

    //로그인 status 체크
    var myPagePostStatus = MutableLiveData<Int>()

    //유저 아이디
    var userId = MutableLiveData<Int>()

    private val _personalInfo = MutableLiveData<UserInfoData>()
    val personalInfo: LiveData<UserInfoData>
        get() = _personalInfo

    val modifyInfo = MutableLiveData<MyPageModifyData>()
    val versionInfo = MutableLiveData<MyPageVersionData>()
    val logOut: MutableLiveData<MyPageLogOutData> = MutableLiveData()
    val blockList = MutableLiveData<MyPageBlockData>()
    val blockUpdate = MutableLiveData<MyPageBlockUpdateData>()
    val resetPassword: MutableLiveData<MyPageResetPasswordData> = MutableLiveData()
    //val quitInfo : MutableLiveData<MyPageQuitData> = MutableLiveData()

    //아이템 position
    var itemPosition = MutableLiveData<Int>()

    private var _myPagePersonal = MutableLiveData<UserInfoData>()
    val myPagePersonal: LiveData<UserInfoData>
        get() = _myPagePersonal

    private var _status = MutableLiveData<Int?>()
    val status: LiveData<Int?> = _status

    private var _quitInfo = MutableLiveData<MyPageQuitData?>()
    val quitInfo: LiveData<MyPageQuitData?>
        get() = _quitInfo

    private val _firstMajorName = MutableLiveData<String>()
    val firstMajorName: LiveData<String>
        get() = _firstMajorName

    private val _secondMajorName = MutableLiveData<String>()
    val secondMajorName: LiveData<String>
        get() = _secondMajorName

    private val _editFinish = MutableLiveData<Boolean>()
    val editFinish: LiveData<Boolean>
        get() = _editFinish

    //토스트
    var reportStatusInfo = MutableLiveData<Int>()

    //유저가 쓴 글
    private val _userPost = MutableLiveData<List<UserPostData>>()
    val userPost: LiveData<List<UserPostData>>
        get() = _userPost

    //유저가 쓴 답글
    private val _userComment = MutableLiveData<List<UserPostData>>()
    val userComment: LiveData<List<UserPostData>>
        get() = _userComment

    //유저가 쓴 후기
    private val _userReview = MutableLiveData<List<UserReviewData.Review>>()
    val userReview: LiveData<List<UserReviewData.Review>>
        get() = _userReview

    //유저가 좋아요 한 글
    private val _userLike = MutableLiveData<List<UserLikeData>>()
    val userLike: LiveData<List<UserLikeData>>
        get() = _userLike

    //유저 1:1질문
    private val _userQuestion = MutableLiveData<List<UserQuestionData>>()
    val userQuestion : LiveData<List<UserQuestionData>>
    get() = _userQuestion

    //마이페이지 내가 쓴 글 조회
    fun getMyPost(filter: String) {
        viewModelScope.launch {
            kotlin.runCatching { userRepository.getUserPost(filter) }
                .onSuccess {
                    _userPost.value = it
                    Timber.d("내가 쓴 글 조회 : 서버통신 성공")
                }
                .onFailure {
                    Timber.d("내가 쓴 글 조회 : 서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 버전정보
    fun getMyPageVersion() {
        viewModelScope.launch {
            kotlin.runCatching { myPageRepository.getMyPageVersion() }
                .onSuccess {
                    versionInfo.value = it
                    Timber.d("mypageVersion : 서버 통신 성공")
                }
                .onFailure {
                    Timber.d("mypageVersion : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //커뮤니티 메인 학과 즐겨 찾기
    fun postCommunityFavorite(majorId: Int) {
        viewModelScope.launch {
            majorRepository.deleteMajorList()
            favoritesRepository.postFavorites(
                FavoritesParam(majorId)
            ).catch {
                Timber.d("즐겨찾기 실패")
            }
                .collectLatest {
                    _communityFavorites.value = it
                }
        }
    }


    //마이페이지 1:1 질문
    fun getMyPageQuestion(userId: Int, sort: String = "recent") {
        viewModelScope.launch {
            kotlin.runCatching { userRepository.getUserQuestion(userId, sort) }
                .onSuccess {
                    _userQuestion.value = it
                    Timber.d("mypageQuestion : 서버 통신 성공")
                }
                .onFailure {
                    Timber.d("mypageQuestion : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }

        }
    }

    //마이페이지 내가 쓴 학과 후기글
    fun getMyPageReview(userId: Int) {
        viewModelScope.launch {
            kotlin.runCatching { userRepository.getUserReview(userId) }
                .onSuccess {
                    _userReview.value = it
                    Timber.d("userReview : 서버 통신 성공")
                }
                .onFailure {
                    Timber.d("userReview : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 좋아요 리스트 (Review)
    fun getMyPageLike(filter: String) {
        viewModelScope.launch {
            kotlin.runCatching { userRepository.getUserLike(filter) }
                .onSuccess {
                    _userLike.value = it
                    Timber.d("mypageLike : 서버 통신 성공")
                }
                .onFailure {
                    Timber.d("mypageLike : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 내가 쓴 답글
    fun getMyPageReply(filter: String) {
        viewModelScope.launch {
            kotlin.runCatching { userRepository.getUserComment(filter) }
                .onSuccess {
                    _userComment.value = it
                    Timber.d("userComment : 서버 통신 성공")
                }
                .onFailure {
                    Timber.d("userComment : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }

        }
    }

    //마이페이지 개인 정보 서버통신
    fun getPersonalInfo(userId: Int) {
        viewModelScope.launch {
            kotlin.runCatching { userRepository.getUserInfo(userId) }
                .onSuccess {
                    _personalInfo.value = it
                    Timber.d("myPageInfo : 서버 통신 완료")
                }
                .onFailure {
                    Timber.d("myPageInfo : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 내 정보 수정 서버통신
    fun putMyPageModify(myPageModifyItem: MyPageModifyItem) {
        viewModelScope.launch {
            kotlin.runCatching { myPageRepository.putMyPageModify(myPageModifyItem) }
                .onSuccess {
                    modifyInfo.value = it
                    Timber.d("MyPageModify : 서버 통신 완료")
                }
                .onFailure {
                    Timber.d("MyPageModify : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 차단 & 차단 해제
    fun postMyPageBlockUpdate(myPageBlockUpdateItem: MyPageBlockUpdateItem) {
        viewModelScope.launch {
            kotlin.runCatching { myPageRepository.postMyPageBlockUpdate(myPageBlockUpdateItem) }
                .onSuccess {
                    blockUpdate.value = it
                    Timber.d("MyPageBlockUpdate : 서버 통신 완료")
                }
                .onFailure {
                    Timber.d("MyPageBlockUpdate : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 로그아웃
    fun postMyPageLogOut() {
        viewModelScope.launch {
            kotlin.runCatching { myPageRepository.postMyPageLogOut() }
                .onSuccess {
                    logOut.value = it
                    Timber.d("MyPageLogOut : 서버 통신 완료")
                }
                .onFailure {
                    Timber.d("MyPageLogOut : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }


    //마이페이지 비밀번호 재설정
    fun postMyPageRestPassword(myPageResetPasswordItem: MyPageResetPasswordItem) {
        viewModelScope.launch {

            when (safeApiCall(Dispatchers.IO) {
                myPageRepository.postMyPageResetPassword(
                    myPageResetPasswordItem
                )
            }) {
                is ResultWrapper.Success -> resetPassword.value =
                    MyPageResetPasswordData("", 200, true)
                is ResultWrapper.NetworkError -> {
                    Timber.d("MyPageResetPw : 네트워크 실패")
                    resetPassword.value = MyPageResetPasswordData("", 500, false)
                }
                is ResultWrapper.GenericError -> {
                    Timber.d("MyPageResetPw : 존재하지 않는 이메일")
                    resetPassword.value = MyPageResetPasswordData("", 400, false)
                }
            }
        }
    }

    //마이페이지 차단된 사용자 목록 조회
    fun getMyPageBlock() {
        viewModelScope.launch {
            kotlin.runCatching { myPageRepository.getMyPageBlock() }
                .onSuccess {
                    blockList.value = it
                    Timber.d("MyPageBlock : 서버 통신 완료")
                }
                .onFailure {
                    Timber.d("MyPageBlock : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 탈퇴
    fun deleteMyPageQuit(myPageQuitItem: MyPageQuitItem) {
        viewModelScope.launch {
            when (val quitData =
                safeApiCall(Dispatchers.IO) { myPageRepository.deleteMyPageQuit(myPageQuitItem) }) {
                is ResultWrapper.Success -> {
                    _quitInfo.value = quitInfo.value?.let { MyPageQuitData(it.data, 200, true) }
                    reportStatusInfo.value = 200
                }
                is ResultWrapper.NetworkError -> {
                    Timber.d("MyPageQuit : 네트워크 실패")
                }
                is ResultWrapper.GenericError -> {
                    Timber.d("MyPageResetPw : 존재하지 않는 비밀번호")
                    reportStatusInfo.value = quitData.code ?: 0
                }
            }
                .also {
                    onLoadingEnd.value = true
                }

        }
    }

    //학과 리스트 가져오기
    fun getMajorList(
        universityId: Int, filter: String, exclude: String?,
        userId: Int
    ) {
        viewModelScope.launch {
            majorRepository.getMajorList(universityId, filter, exclude, userId)
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("학과 리스트 가져오기 실패 ${it.printStackTrace()}")

                }
                .collectLatest {
                    _majorList.value = it
                    Timber.d("학과 리스트 $it")
                }
        }
    }


    // 학과 이름
    fun getMajorName(isFirstMajor: Boolean, majorId: Int) {
        viewModelScope.launch {

            runBlocking {
                kotlin.runCatching {


                    runCatching { getMajorInfoDataUseCase(majorId) }
                        .onSuccess {
                            if (isFirstMajor)
                                _firstMajorName.value = it.majorName
                            else
                                _secondMajorName.value = it.majorName
                            Timber.d("MyPageGetMajor : 서버 통신 성공")
                        }
                        .onFailure {
                            Timber.d("MyPageGetMajor : 서버 통신 실패")
                        }
                }
            }
        }

    }

    // 저장 완료
    fun editFinish() {
        _editFinish.value = true

    }


    val postCurFragment: MutableLiveData<Int>
        get() = MyPageViewModel.postCurFragment

    val applyCurFragment: MutableLiveData<Int>
        get() = MyPageViewModel.applyCurFragment

    val likeCurFragment: MutableLiveData<Int>
        get() = MyPageViewModel.likeCurFragment

    companion object {
        val postCurFragment = MutableLiveData(-1)
        val applyCurFragment = MutableLiveData(-1)
        val likeCurFragment = MutableLiveData(-1)
    }
}

