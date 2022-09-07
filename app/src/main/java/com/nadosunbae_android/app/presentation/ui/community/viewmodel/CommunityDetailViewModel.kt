package com.nadosunbae_android.app.presentation.ui.community.viewmodel

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
import com.nadosunbae_android.domain.model.post.PostDetailData
import com.nadosunbae_android.domain.repository.post.PostRepository
import com.nadosunbae_android.domain.usecase.classroom.*
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommunityDetailViewModel @Inject constructor(
    private val postRepository : PostRepository,
    val getInformationDetailUseCase: GetInformationDetailUseCase,
    val postQuestionCommentWriteUseCase: PostQuestionCommentWriteUseCase,
    val postLikeDataUseCase: PostLikeDataUseCase,
    val deleteCommentDataUseCase: DeleteCommentDataUseCase,
    val postReportUseCase: PostReportUseCase,
    val deletePostDataUseCase: DeletePostDataUseCase,
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()

    override var dropDownSelected = MutableLiveData<SelectableData>()


    //정보 상세 조회
    private val _communityDetailData = MutableStateFlow(PostDetailData.DEFAULT)
    val communityDetailData: StateFlow<PostDetailData>
        get() = _communityDetailData

    //정보 댓글 등록
    var registerInfoComment = MutableLiveData<QuestionCommentWriteData>()

    //정보 좋아요를 위한 postId
    private var _postId = MutableLiveData<String>()
    val postId: LiveData<String>
        get() = _postId

    fun setPostId(postId: String) {
        _postId.value = postId
    }

    //정보 댓글 commentId
    var commentId = MutableLiveData<Int>()

    //정보 댓글 및 원글 분류
    var divisionPost = MutableLiveData<Int>()

    //댓글 position
    var position = MutableLiveData<Int>()

    //댓글 삭제 데이터
    private var _deleteComment = MutableLiveData<DeleteCommentData>()
    val deleteComment: LiveData<DeleteCommentData>
        get() = _deleteComment



    //부적절 사용자 데이터들
    private var _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int>
        get() = _statusCode

    private var _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    // 좋아요 데이터
    private var _postLike = MutableLiveData<LikeData>()
    val postLike: LiveData<LikeData>
        get() = _postLike

    //좋아요 데이터 저장
    private fun setPostLike(likeData: LikeData) {
        _postLike.value = likeData
    }

    //작성자 Id
    var writerId = MutableLiveData<Int>()

    //유저 Id
    var userId = MutableLiveData<Int>()

    //신고 데이터
    private var _reportData = MutableLiveData<ReportData?>()
    val reportData: LiveData<ReportData?>
        get() = _reportData

    //신고 사유
    var reportReasonInfo = MutableLiveData<String>()

    //신고 토스트위한
    var reportStatusInfo = MutableLiveData<Int>()

    //원글 삭제 데이터
    private var _deletePostData = MutableLiveData<DeleteCommentData>()
    val deletePostData: LiveData<DeleteCommentData>
        get() = _deletePostData

    //커뮤니티 상세 서버통신
    fun getPostDetail(postId: String) {
        viewModelScope.launch {
            postRepository.getPostDetail(postId)
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("CommunityDetail : 정보 상세보기 서버 통신 실패")
                }
                .collectLatest {
                    _communityDetailData.value = it
                    Timber.d("CommunityDetail 서버 통신 성공")
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
    fun postClassRoomInfoLike(likeItem: LikeItem) {
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
    fun deleteComment(commentId: Int) {
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
    fun postReport(reportItem: ReportItem) {
        viewModelScope.launch {
            when (val reportData =
                safeApiCall(Dispatchers.IO) { postReportUseCase(reportItem) }) {
                is ResultWrapper.Success -> {
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
    fun deletePost(postId: Int) {
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

