package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.model.classroom.SeniorPersonalData
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.mypage.MyPageBlockUpdateData
import com.nadosunbae_android.domain.model.mypage.MyPageBlockUpdateItem
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import com.nadosunbae_android.domain.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SeniorPersonalViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val myPageRepository: MyPageRepository
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    override var dropDownSelected = MutableLiveData<SelectableData>()


    //선배 개인페이지
    private val _seniorPersonal = MutableStateFlow(
        SeniorPersonalData("", "", false, "", 0, "", "", 0, 0, null, null)
    )
    val seniorPersonal: StateFlow<SeniorPersonalData>
        get() = _seniorPersonal

    //선배 1:1 질문
    private val _seniorQuestion = MutableStateFlow(
        listOf<ClassRoomData>()
    )
    val seniorQuestion: StateFlow<List<ClassRoomData>>
        get() = _seniorQuestion

    //선배 userId
    var seniorId = MutableLiveData<Int>()

    //선배 차단
    private var _blockData = MutableLiveData<MyPageBlockUpdateData>()
    val blockData: LiveData<MyPageBlockUpdateData>
        get() = _blockData

    //질문 가능 여부
    private var _isQuestion = MutableLiveData<Boolean>()
    val isQuestion: LiveData<Boolean>
        get() = _isQuestion

    //선배 개인페이지 정보 서버통신
    fun getSeniorPersonal(userId: Int) {
        viewModelScope.launch {
            runCatching { userRepository.getUserInfo(userId) }
                .onSuccess {
                    _seniorPersonal.value = SeniorPersonalData(
                        firstMajorName = it.firstMajorName,
                        firstMajorStart = it.firstMajorStart,
                        isOnQuestion = it.isOnQuestion,
                        nickname = it.nickname,
                        profileImageId = it.profileImageId ?: 0,
                        secondMajorName = it.secondMajorName,
                        secondMajorStart = it.secondMajorStart,
                        userId = it.userId,
                        count = it.count,
                        bio = it.bio,
                        rate = it.responseRate
                    )
                    Timber.d("seniorPersonal : 선배 개인페이지 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("seniorPersonal : 선배 개인페이지 서버 통신 실패")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    //선배 1:1 질문 리스트
    fun getSeniorQuestionList(userId: Int, sort: String) {
        viewModelScope.launch {
            runCatching { userRepository.getUserQuestion(userId, sort) }
                .onSuccess {
                    _seniorQuestion.value = it.filter { it ->
                        it.id != userId
                        }
                        .map {
                        ClassRoomData(
                            postId = it.postId,
                            title = it.title,
                            content = it.content,
                            createdAt = it.createdAt,
                            writer = ClassRoomData.Writer(
                                nickname = it.nickname,
                                profileImageId = 0,
                                writerId = it.id
                            ),
                            likeCount = it.likeCount,
                            isLiked = it.isLiked,
                            commentCount = it.commentCount
                        )
                    }
                    Timber.d("seniorQuestion : 선배 1:1질문 서버 통신 완료")
                }
                .onFailure {
                    Timber.d("seniorQuestion : 선배 1:1질문 서버 통신 실패")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    //과방 차단 & 차단 해제
    fun postClassRoomBlockUpdate(myPageBlockUpdateItem: MyPageBlockUpdateItem) {
        viewModelScope.launch {
            kotlin.runCatching { myPageRepository.postMyPageBlockUpdate(myPageBlockUpdateItem) }
                .onSuccess {
                    _blockData.value = it
                    Timber.d("classRoomBlockUpdate 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("classRoomBlockUpdate 서버 통신 실패")
                }

        }
    }

    override val onLoadingEnd = MutableLiveData<Boolean>()
}


