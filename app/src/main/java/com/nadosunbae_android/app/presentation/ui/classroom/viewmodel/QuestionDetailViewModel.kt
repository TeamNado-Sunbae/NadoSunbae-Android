package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.domain.model.classroom.*
import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.usecase.classroom.GetQuestionDetailDataUseCase
import com.nadosunbae_android.domain.usecase.classroom.PostQuestionCommentWriteUseCase
import com.nadosunbae_android.domain.usecase.classroom.PutCommentUpdateUseCase
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import kotlinx.coroutines.launch

class QuestionDetailViewModel(
    val getQuestionDetailDataUseCase : GetQuestionDetailDataUseCase,
    val postQuestionCommentWriteUseCase: PostQuestionCommentWriteUseCase,
    val postLikeDataUseCase : PostLikeDataUseCase,
    val putCommentUpdateUseCase: PutCommentUpdateUseCase
) : ViewModel(), DropDownSelectableViewModel {

    override var dropDownSelected = MutableLiveData<SelectableData>()

    //전체 질문 상세보기 데이터
    private var _questionDetailData = MutableLiveData<QuestionDetailData>()
    val questionDetailData : LiveData<QuestionDetailData>
        get() = _questionDetailData

    //답글, 질문, 질문에대한 답글 뷰 넘버 ( 1 -> 질문자, 2 -> 답변자 )
    var viewNum = MutableLiveData<Int>()
    var position = MutableLiveData<Int>()
    //댓글 등록
    var registerComment = MutableLiveData<QuestionCommentWriteData>()

    //postId
    var postId = MutableLiveData<Int>()

    // 좋아요를 위한 postId 설정
    private var _likePostId = MutableLiveData<Int>()
    val likePostId : LiveData<Int>
        get() = _likePostId

    // 좋아요 postId 설정
    fun setLikePostId(postId : Int){
        _likePostId.value = postId
    }

    // 댓글 수정 데이터
    private var _commentUpdate = MutableLiveData<CommentUpdateData>()
    val commentUpdate : LiveData<CommentUpdateData>
        get() = _commentUpdate

    //전체 질문 1:1 질문 구분
    private var _divisionQuestion = MutableLiveData<Int>()
    val divisionQuestion : LiveData<Int>
        get() = _divisionQuestion

    fun setDivisionQuestion(num : Int){
        _divisionQuestion.value = num
    }

    // 좋아요 데이터
    private var _postLike = MutableLiveData<LikeData>()
    val postLike : LiveData<LikeData>
        get() = _postLike


    //좋아요 데이터 저장
    private fun setPostLike(likeData : LikeData){
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

    //댓글 수정 서버통신
    fun putCommentUpdate(commentId : Int, commentUpdateItem : CommentUpdateItem){
        viewModelScope.launch {
            runCatching { putCommentUpdateUseCase(commentId, commentUpdateItem) }
                .onSuccess {
                    _commentUpdate.value = it
                    Log.d("commentUpdate", "댓글 수정 성공 ")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("commentUpdate", "댓글 수정 실패 ")
                }

        }
    }
}