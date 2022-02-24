package com.nadosunbae_android.app.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.domain.model.mypage.*
import com.nadosunbae_android.domain.model.sign.SignInItem
import com.nadosunbae_android.domain.usecase.mypage.*
import kotlinx.coroutines.launch

class MyPageViewModel(
    val getMyPageMyInfoUseCase: GetMyPageMyInfoUseCase,
    val getMyPageQuestionUseCase: GetMyPageQuestionUseCase,
    val putMyPageModifyUseCase: PutMyPageModifyUseCase,
    val getMyPagePostUseCase: GetMyPagePostUseCase,
    val getMyPageReplyUseCase: GetMyPageReplyUseCase,
    val getMyPageVersionUseCase: GetMyPageVersionUseCase,
    val postMyPageLogOutUseCase : PostMyPageLogOutUseCase,
    val getMyPageLikeQuestionUseCase: GetMyPageLikeQuestionUseCase,
    val getMyPageLikeReviewUseCase: GetMyPageLikeReviewUseCase,
    val getMyPageReviewUseCase: GetMyPageReviewUseCase

    ) : ViewModel() {

    // 로그인 response 데이터
    private val _signData = MutableLiveData<SignInItem.User>()
    val signData: LiveData<SignInItem.User>
        get() = _signData

    //유저 아이디
    var userId = MutableLiveData<Int>()


    val personalQuestion = MutableLiveData<MyPageQuestionData>()
    val personalInfo = MutableLiveData<MyPageMyInfo>()
    val modifyInfo = MutableLiveData<MyPageModifyData>()
    val postByMe = MutableLiveData<MyPagePostData>()
    val replyByMe = MutableLiveData<MyPageReplyData>()
    val versionInfo = MutableLiveData<MyPageVersionData>()
    val logOut: MutableLiveData<MyPageLogOutData> = MutableLiveData()
    val likeQuestion = MutableLiveData<MyPageLikeQuestionData>()
    val likeReview = MutableLiveData<MyPageLikeReviewData>()
    val reviewList = MutableLiveData<MyPageReviewData>()


    private var _myPagePersonal = MutableLiveData<MyPageMyInfo>()
    val myPagePersonal : LiveData<MyPageMyInfo>
    get() = _myPagePersonal


    fun setSignData(signData: SignInItem.User) {
        _signData.value = signData
        userId.value = signData.userId
    }

    //마이페이지 버전정보
    fun getMyPageVersion() {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageVersionUseCase() }
                .onSuccess {
                    versionInfo.value = it
                    Log.d("mypageVersion", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("mypageVersion", "서버 통신 실패")
                }
        }
    }


    //마이페이지 1:1 질문
    fun getMyPageQuestion(userId: Int, sort: String = "recent") {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageQuestionUseCase(userId, sort) }
                .onSuccess {
                    personalQuestion.value = it
                    Log.d("mypageQuestion", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("mypageQuestion", "서버 통신 실패")
                }

        }
    }

    //마이페이지 내가 쓴 학과 후기글
    fun getMyPageReview(userId: Int) {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageReviewUseCase(userId) }
                .onSuccess {
                    reviewList.value = it
                    Log.d("mypageReview", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("mypageReview", "서버 통신 실패")
                }
        }
    }

    //마이페이지 좋아요 리스트 (Review)
    fun getMyPageLikeReview(type: String = "review") {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageLikeReviewUseCase(type) }
                .onSuccess {
                    likeReview.value = it
                    Log.d("mypageLikeReview", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("mypageLikeReview", "서버 통신 실패")
                }
        }
    }

    //마이페이지 좋아요 리스트 (Question)
    fun getMyPageLikeQuestion(type: String = "question") {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageLikeQuestionUseCase(type) }
                .onSuccess {
                    likeQuestion.value = it
                    Log.d("mypageLikeQuestion", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("mypageLikeQuestion", "서버 통신 실패")
                }
        }
    }

    //마이페이지 내가 쓴 글
    fun getMyPagePost(type: String) {
        viewModelScope.launch {
            kotlin.runCatching { getMyPagePostUseCase(type) }
                .onSuccess {
                    postByMe.value = it
                    Log.d("mypagePost", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("mypagePost", "서버 통신 실패")
                }

        }
    }

    //마이페이지 내가 쓴 답글
    fun getMyPageReply(postTypeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageReplyUseCase(postTypeId) }
                .onSuccess {
                    replyByMe.value = it
                    Log.d("mypageReply", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("mypageReply", "서버 통신 실패")
                }

        }
    }

    //마이페이지 개인 정보 서버통신
    fun getPersonalInfo(userId: Int){
        viewModelScope.launch {
            kotlin.runCatching { getMyPageMyInfoUseCase(userId) }
                .onSuccess {
                    personalInfo.value = it
                    Log.d("myPageInfo", "서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("myPageInfo", "서버 통신 실패")
                }
        }
    }

    //마이페이지 내 정보 수정 서버통신
    fun putMyPageModify(myPageModifyItem: MyPageModifyItem) {
        viewModelScope.launch {
            kotlin.runCatching { putMyPageModifyUseCase(myPageModifyItem) }
                .onSuccess {
                    modifyInfo.value = it
                    Log.d("MyPageModify", "서버 통신 완료")
               }
                .onFailure {
                    it.printStackTrace()
                    Log.d("MyPageModify", "서버 통신 실패")
                }
        }
    }

    //마이페이지 로그아웃
    fun postMyPageLogOut() {
        viewModelScope.launch {
            kotlin.runCatching { postMyPageLogOutUseCase() }
                .onSuccess {
                    logOut.value = it
                    Log.d("MyPageLogOut", "서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("MyPageLogOut", "서버 통신 실패")
                }
        }
    }
}

