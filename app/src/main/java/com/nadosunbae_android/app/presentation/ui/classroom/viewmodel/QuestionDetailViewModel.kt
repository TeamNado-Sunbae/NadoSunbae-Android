package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.presentation.ui.classroom.SeniorPersonalFragment
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.app.util.ResultWrapper
import com.nadosunbae_android.app.util.safeApiCall
import com.nadosunbae_android.domain.model.classroom.*
import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.sign.NicknameDuplicationCheck
import com.nadosunbae_android.domain.usecase.classroom.*
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class QuestionDetailViewModel(
    val getQuestionDetailDataUseCase: GetQuestionDetailDataUseCase,
    val postQuestionCommentWriteUseCase: PostQuestionCommentWriteUseCase,
    val postLikeDataUseCase: PostLikeDataUseCase,
    val putCommentUpdateUseCase: PutCommentUpdateUseCase,
    val deleteCommentDataUseCase: DeleteCommentDataUseCase,
    val deletePostDataUseCase: DeletePostDataUseCase,
    val postReportUseCase: PostReportUseCase
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()

    override var dropDownSelected = MutableLiveData<SelectableData>()

    //전체 질문 상세보기 데이터
    private var _questionDetailData = MutableLiveData<QuestionDetailData>()
    val questionDetailData: LiveData<QuestionDetailData>
        get() = _questionDetailData

    //답글, 질문, 질문에대한 답글 뷰 넘버 ( 1 -> 질문자, 2 -> 답변자 )
    var viewNum = MutableLiveData<Int>()

    //아이템 위치 정보
    var position = MutableLiveData<Int>()

    // 댓글 id
    var commentId = MutableLiveData<Int>()

    //댓글 등록
    var registerComment = MutableLiveData<QuestionCommentWriteData>()

    //postId
    var postId = MutableLiveData<Int>()

    //댓글 삭제 분류 1 -> comment, 2-> write
    var deleteNum = MutableLiveData<Int>()

    //신고 사유 데이터 및 후기, 과방, 댓글 구분
    var reportReason = MutableLiveData<String>()

    //부적절 사용자 데이터들
    private var _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int>
        get() = _statusCode

    private var _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    //댓글 삭제 데이터
    private var _deleteData = MutableLiveData<DeleteCommentData>()
    val deleteData: LiveData<DeleteCommentData>
        get() = _deleteData

    //원글 삭제 데이터
    private var _deletePostData = MutableLiveData<DeleteCommentData>()
    val deletePostData: LiveData<DeleteCommentData>
        get() = _deletePostData

    // 좋아요를 위한 postId 설정
    private var _likePostId = MutableLiveData<Int>()
    val likePostId: LiveData<Int>
        get() = _likePostId

    // 좋아요 postId 설정
    fun setLikePostId(postId: Int) {
        _likePostId.value = postId
    }

    // 댓글 수정 데이터
    private var _commentUpdate = MutableLiveData<CommentUpdateData>()
    val commentUpdate: LiveData<CommentUpdateData>
        get() = _commentUpdate

    //신고 데이터
    private var _reportData = MutableLiveData<ReportData?>()
    val reportData: LiveData<ReportData?>
        get() = _reportData

    //신고 status 체크
    var reportStatus = MutableLiveData<Int>()

    //전체 질문 1:1 질문 구분
    private var _divisionQuestion = MutableLiveData<Int>()
    val divisionQuestion: LiveData<Int>
        get() = _divisionQuestion

    fun setDivisionQuestion(num: Int) {
        _divisionQuestion.value = num
    }

    // 좋아요 데이터
    private var _postLike = MutableLiveData<LikeData>()
    val postLike: LiveData<LikeData>
        get() = _postLike


    //좋아요 데이터 저장
    private fun setPostLike(likeData: LikeData) {
        _postLike.value = likeData
    }


    //전체 질문 상세보기 서버 통신
    fun getClassRoomQuestionDetail(postId: Int) {
        viewModelScope.launch {
            when (val questionDetail =
                safeApiCall(Dispatchers.IO) { getQuestionDetailDataUseCase(postId) }) {
                is ResultWrapper.Success -> {
                    _statusCode.value = 200
                    Timber.d("questionDetail : 서버 통신 성공")
                }
                is ResultWrapper.NetworkError -> {
                    Timber.d("questionDetail : 네트워크 실패")

                }
                is ResultWrapper.GenericError -> {
                    Timber.d("questionDetail :사용자 에러")
                    _message.value = questionDetail.message ?: ""
                    _statusCode.value = questionDetail.code ?: 0
                    Timber.d("questionDetail : ${questionDetail.message}")
                    Timber.d("questionDetail : ${questionDetail.code}")
                }
            }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    // 질문 좋아요 및 좋아요 취소 서버 통신
    fun postClassRoomLike(likeItem: LikeItem) {
        viewModelScope.launch {
            runCatching { postLikeDataUseCase(likeItem) }
                .onSuccess {
                    setPostLike(it)
                    Timber.d("classRoomPostLike : 좋아요 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("classRoomPostLike : 좋아요 서버 통신 실패!")
                }.also {
                    onLoadingEnd.value = true
                }
        }

    }


    //댓글 등록 서버 통신
    fun postQuestionCommentWrite(questionCommentWriteItem: QuestionCommentWriteItem) {
        viewModelScope.launch {
            runCatching { postQuestionCommentWriteUseCase(questionCommentWriteItem) }
                .onSuccess {
                    registerComment.value = it
                    Timber.d("questionComment : 댓글 통신 성공!")
                    replyQuestionAnalytics()
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("questionComment : 댓글 통신 실패!")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    //댓글 수정 서버통신
    fun putCommentUpdate(commentId: Int, commentUpdateItem: CommentUpdateItem) {
        viewModelScope.launch {
            runCatching { putCommentUpdateUseCase(commentId, commentUpdateItem) }
                .onSuccess {
                    _commentUpdate.value = it
                    Timber.d("commentUpdate : 댓글 수정 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("commentUpdate : 댓글 수정 실패!")
                }.also {
                    onLoadingEnd.value = true
                }

        }
    }

    //댓글 삭제 서버통신
    fun deleteComment(commentId: Int) {
        viewModelScope.launch {
            runCatching { deleteCommentDataUseCase(commentId) }
                .onSuccess {
                    _deleteData.value = it
                    Timber.d("deleteComment : 댓글 삭제 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("deleteComment : 댓글 삭제 실패!")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    // 원글 삭제 서버통신
    fun deletePost(postId: Int) {
        viewModelScope.launch {
            runCatching { deletePostDataUseCase(postId) }
                .onSuccess {
                    _deletePostData.value = it
                    Timber.d("deletePost : 원글 삭제 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("deletePost : 원글 삭제 실패!")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    //신고하기 서버통신
    fun postReport(reportItem: ReportItem) {
        viewModelScope.launch {
            when (val reportData =
                safeApiCall(Dispatchers.IO) { postReportUseCase(reportItem) }) {
                is ResultWrapper.Success -> {
                    _reportData.value = reportData.data
                    reportStatus.value = 200
                }
                is ResultWrapper.NetworkError -> {
                    Timber.d("postReport : 네트워크 실패")
                }
                is ResultWrapper.GenericError -> {
                    Timber.d("postReport : 사용자 에러")
                    reportStatus.value = reportData.code ?: 0
                }
            }
        }
    }

    private fun replyQuestionAnalytics() {
        // question_reply analytics
        var isNewReply = true
        val data = _questionDetailData.value
        if (data != null) {
            for (m in data.messageList) {   // 기존 대화에서 모든 메시지가 질문자의 것일 경우 isNewReply = true
                if (!m.isQuestioner)
                    isNewReply = false
            }

            if (isNewReply)
                FirebaseAnalyticsUtil.question(FirebaseAnalyticsUtil.Question.QUESTION_REPLY)

        }
    }
}



