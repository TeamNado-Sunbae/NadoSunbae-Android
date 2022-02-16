package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteData
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.domain.model.classroom.QuestionDetailData
import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.usecase.classroom.GetQuestionDetailDataUseCase
import com.nadosunbae_android.domain.usecase.classroom.PostQuestionCommentWriteUseCase
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import kotlinx.coroutines.launch

class QuestionDetailViewModel(
    val getQuestionDetailDataUseCase : GetQuestionDetailDataUseCase,
    val postQuestionCommentWriteUseCase: PostQuestionCommentWriteUseCase,
    val postLikeDataUseCase : PostLikeDataUseCase
) : ViewModel() {


    //전체 질문 상세보기 데이터
    private var _questionDetailData = MutableLiveData<QuestionDetailData>()
    val questionDetailData : LiveData<QuestionDetailData>
        get() = _questionDetailData


    //댓글 등록
    var registerComment = MutableLiveData<QuestionCommentWriteData>()


    // 좋아요를 위한 postId 설정
    private var _likePostId = MutableLiveData<Int>()
    val likePostId : LiveData<Int>
        get() = _likePostId

    fun setLikePostId(postId : Int){
        _likePostId.value = postId
    }

    // 좋아요 데이터
    private var _postLike = MutableLiveData<LikeData>()
    val postLike : LiveData<LikeData>
        get() = _postLike

    fun setPostLike(likeData : LikeData){
        _postLike.value = likeData
    }

    //전체 질문 상세보기 서버 통신
    fun getClassRoomQuestionDetail(postId : Int){
        viewModelScope.launch {
            runCatching { getQuestionDetailDataUseCase(postId) }
                .onSuccess {
                    _questionDetailData.value = it
                    Log.d("classRoomDetail", "메인 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("classRoomMain", "메인 서버 통신 실패")
                }
        }
    }
    // 질문 좋아요 및 좋아요 취소 서버 통신
    fun postClassRoomLike(likeItem : LikeItem){
        viewModelScope.launch {
            runCatching { postLikeDataUseCase(likeItem) }
                .onSuccess {
                    setPostLike(it)
                    Log.d("classRoomPostLike", "좋아요 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("classRoomPostLike", "좋아요 서버 통신 실패!")
                }
        }

    }



    //댓글 등록 서버 통신
    fun postQuestionCommentWrite(questionCommentWriteItem: QuestionCommentWriteItem){
        viewModelScope.launch {
            runCatching {postQuestionCommentWriteUseCase(questionCommentWriteItem)  }
                .onSuccess {
                    registerComment.value = it
                    Log.d("questionComment", "댓글 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("questionComment", "댓글 통신 실패 ")
                }
        }
    }
}