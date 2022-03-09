package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.app.util.ResultWrapper
import com.nadosunbae_android.app.util.safeApiCall
import com.nadosunbae_android.domain.model.classroom.*
import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.usecase.classroom.*
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class InfoDetailViewModel(
    val getInformationDetailUseCase: GetInformationDetailUseCase,
    val postQuestionCommentWriteUseCase: PostQuestionCommentWriteUseCase,
    val postLikeDataUseCase : PostLikeDataUseCase,
    val deleteCommentDataUseCase: DeleteCommentDataUseCase,
    val postReportUseCase: PostReportUseCase,
    val deletePostDataUseCase: DeletePostDataUseCase,
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()

    override var dropDownSelected = MutableLiveData<SelectableData>()


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

    //정보 댓글 commentId
    var commentId = MutableLiveData<Int>()

    //정보 댓글 및 원글 분류
    var divisionPost = MutableLiveData<Int>()

    //댓글 position
    var position = MutableLiveData<Int>()

    //댓글 삭제 데이터
    private var _deleteComment = MutableLiveData<DeleteCommentData>()
    val deleteComment : LiveData<DeleteCommentData>
        get() = _deleteComment

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

    //작성자 Id
    var writerId = MutableLiveData<Int>()

    //유저 Id
    var userId = MutableLiveData<Int>()

    //신고 데이터
    private var _reportData = MutableLiveData<ReportData?>()
    val reportData : LiveData<ReportData?>
        get() = _reportData

    //신고 사유
    var reportReasonInfo = MutableLiveData<String>()

    //신고 토스트위한
    var reportStatusInfo = MutableLiveData<Int>()

    //원글 삭제 데이터
    private var _deletePostData = MutableLiveData<DeleteCommentData>()
    val deletePostData : LiveData<DeleteCommentData>
        get() = _deletePostData

    //정보 상세 조회 서버통신
    fun getInfoDetail(postId: Int) {
        viewModelScope.launch {
            runCatching { getInformationDetailUseCase(postId) }
                .onSuccess {
                    _infoDetailData.value = it
                    Timber.d("InfoDetail : 정보 상세보기 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("InfoDetail : 정보 상세보기 서버 통신 실패")
                }.also {
            onLoadingEnd.value = true
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
                    Timber.d("InfoComment : 댓글 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("InfoComment : 댓글 통신 실패")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    // 정보 상세 좋아요
    fun postClassRoomInfoLike(likeItem : LikeItem){
        viewModelScope.launch {
            runCatching { postLikeDataUseCase(likeItem) }
                .onSuccess {
                    setPostLike(it)
                    Timber.d("InformationPostLike : 좋아요 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("InformationPostLike : 좋아요 서버 통신 실패!")
                }.also {
                    onLoadingEnd.value = true
                }
        }

    }

    //정보 댓글 삭제
    //댓글 삭제 서버통신
    fun deleteComment(commentId : Int){
        viewModelScope.launch {
            runCatching { deleteCommentDataUseCase(commentId) }
                .onSuccess {
                    _deleteComment.value = it
                    Timber.d("deleteComment : 댓글 삭제 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("deleteComment : 댓글 삭제 실패")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }


    //신고하기 서버통신
    fun postReport(reportItem: ReportItem){
        viewModelScope.launch {
            when (val reportData =
                safeApiCall(Dispatchers.IO) { postReportUseCase(reportItem) }) {
                is ResultWrapper.Success ->  {
                    _reportData.value = reportData.data
                    reportStatusInfo.value = 200
                    Timber.d("postReport : 신고 성공!")

                }
                is ResultWrapper.NetworkError -> {
                    Timber.d("postReport : 네트워크 실패")

                }
                is ResultWrapper.GenericError -> {
                    Timber.d("postReport : 사용자 에러")
                    reportStatusInfo.value = reportData.code ?: 0
                }
            }
        }
    }


    // 원글 삭제 서버통신
    fun deletePost(postId : Int){
        viewModelScope.launch {
            runCatching { deletePostDataUseCase(postId) }
                .onSuccess {
                    _deletePostData.value = it
                    Timber.d("deletePost : 원글 삭제 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("deletePost : 원글 삭제 실패")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }
}

