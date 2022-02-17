package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.domain.model.classroom.InfoDetailData
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteData
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.usecase.classroom.GetInformationDetailUseCase
import com.nadosunbae_android.domain.usecase.classroom.PostQuestionCommentWriteUseCase
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import kotlinx.coroutines.launch

class InfoDetailViewModel(
    val getInformationDetailUseCase: GetInformationDetailUseCase,
    val postQuestionCommentWriteUseCase: PostQuestionCommentWriteUseCase,
    val postLikeDataUseCase : PostLikeDataUseCase
) : ViewModel() {


    //정보 상세 조회
    private val _infoDetailData = MutableLiveData<InfoDetailData>()
    val infoDetailData: LiveData<InfoDetailData>
        get() = _infoDetailData

    //정보 댓글 등록
    var registerInfoComment = MutableLiveData<QuestionCommentWriteData>()

    //정보 좋아요를 위한 postId
    private var _infoPostId = MutableLiveData<Int>()
    val infoPostId : LiveData<Int>
        get() = _infoPostId

    fun setPostId(postId : Int){
        _infoPostId.value = postId
    }

    // 좋아요 데이터
    private var _postLike = MutableLiveData<LikeData>()
    val postLike : LiveData<LikeData>
        get() = _postLike

    //좋아요 데이터 저장
    private fun setPostLike(likeData : LikeData){
        _postLike.value = likeData
    }

    //정보 상세 조회 서버통신
    fun getInfoDetail(postId: Int) {
        viewModelScope.launch {
            runCatching { getInformationDetailUseCase(postId) }
                .onSuccess {
                    _infoDetailData.value = it
                    Log.d("infoDetail", "정보 상세보기 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("infoDetail", "정보 상세보기 서버 통신 실패")
                }
        }

    }

    //정보 상세 댓글 등록
    fun postInfoCommentWrite(
        questionCommentWriteItem: QuestionCommentWriteItem
    ) {
        viewModelScope.launch {
            runCatching { postQuestionCommentWriteUseCase(questionCommentWriteItem) }
                .onSuccess {
                    registerInfoComment.value = it
                    Log.d("infoComment", "댓글 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("infoComment", "댓글 통신 실패 ")
                }
        }
    }

    // 정보 상세 좋아요
    fun postClassRoomInfoLike(likeItem : LikeItem){
        viewModelScope.launch {
            runCatching { postLikeDataUseCase(likeItem) }
                .onSuccess {
                    setPostLike(it)
                    Log.d("InformationPostLike", "좋아요 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("InformationPostLike", "좋아요 서버 통신 실패!")
                }
        }

    }
}

