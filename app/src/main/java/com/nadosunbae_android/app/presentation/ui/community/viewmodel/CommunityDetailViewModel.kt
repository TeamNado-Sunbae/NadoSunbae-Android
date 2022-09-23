package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.app.util.ResultWrapper
import com.nadosunbae_android.app.util.safeApiCall
import com.nadosunbae_android.domain.model.classroom.ReportData
import com.nadosunbae_android.domain.model.classroom.ReportItem
import com.nadosunbae_android.domain.model.comment.CommentData
import com.nadosunbae_android.domain.model.comment.CommentParam
import com.nadosunbae_android.domain.model.comment.DeleteCommentData
import com.nadosunbae_android.domain.model.like.LikeParam
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.post.PostDeleteData
import com.nadosunbae_android.domain.model.post.PostDetailData
import com.nadosunbae_android.domain.repository.comment.CommentRepository
import com.nadosunbae_android.domain.repository.like.LikeRepository
import com.nadosunbae_android.domain.repository.post.PostRepository
import com.nadosunbae_android.domain.usecase.classroom.PostReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommunityDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val likeRepository: LikeRepository,
    val postReportUseCase: PostReportUseCase,
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()

    override var dropDownSelected = MutableLiveData(SelectableData.DEFAULT)


    //커뮤니티 상세 조회
    private var _communityDetailData = MutableStateFlow(PostDetailData.DEFAULT)
    val communityDetailData: StateFlow<PostDetailData>
        get() = _communityDetailData

    //커뮤니티 댓글
    val commentContent = MutableLiveData<String>()

    //커뮤니티 댓글 데이터
    private var _commentData = MutableStateFlow(CommentData.DEFAULT)
    val commentData: StateFlow<CommentData>
        get() = _commentData


    //좋아요를 위한 postId
    private var _postId = MutableLiveData<String>()
    val postId: LiveData<String>
        get() = _postId

    fun setPostId(postId: String) {
        _postId.value = postId
    }

    //댓글 commentId
    var commentId = MutableLiveData<Int>()

    //정보 댓글 및 원글 분류
    var divisionPost = MutableLiveData<Int>()

    //댓글 position
    var position = MutableLiveData<Int>()

    //원글 삭제 데이터
    private var _deletePostData = MutableStateFlow(PostDeleteData.DEFAULT)
    val deletePostData : StateFlow<PostDeleteData>
        get() = _deletePostData

    fun deletePost(){
        viewModelScope.launch {
            postRepository.deletePost(postId.value ?: "")
                .catch {
                    Timber.d("원글 삭제 실패")
                }
                .collectLatest {
                    _deletePostData.value = it
                    Timber.d("원글 삭제 성공")
                }
        }.also {
            onLoadingEnd.value = true
        }

    }


    //댓글 삭제 데이터
    private var _deleteComment = MutableStateFlow(DeleteCommentData.DEFAULT)
    val deleteComment: StateFlow<DeleteCommentData>
        get() = _deleteComment

    // 메뉴 리스트
    private var _dropDownMenu = MutableLiveData<List<SelectableData>>()
    val dropDownMenu: LiveData<List<SelectableData>>
        get() = _dropDownMenu

    // whatUser 작성자 -> 1, 제 3자 -> 2
    fun setDropDownMenu(whatUser: Int? = 0) {
        _dropDownMenu.value =
            if (whatUser == 1) { //답글
                mutableListOf(SelectableData(1, "삭제", false))
            } else if (whatUser == 2) {
                mutableListOf(SelectableData(2, "신고", false))
            } else {
                //원글
                if (MainGlobals.signInData?.userId == communityDetailData.value.writerId) {
                    mutableListOf(
                        SelectableData(1, "수정", false),
                        SelectableData(2, "삭제", false)
                    )
                } else {
                    mutableListOf(SelectableData(2, "신고", false))
                }
            }
    }



    //신고 데이터
    private var _reportData = MutableLiveData<ReportData?>()
    val reportData: LiveData<ReportData?>
        get() = _reportData

    //신고 사유
    var reportReasonInfo = MutableLiveData<String>()

    //신고 토스트위한
    var reportStatusInfo = MutableLiveData<Int>()


    //커뮤니티 상세 서버통신
    fun getPostDetail() {
        viewModelScope.launch {
            postRepository.getPostDetail(_postId.value ?: "")
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("CommunityDetail : 정보 상세보기 서버 통신 실패")
                }
                .collectLatest {
                    Timber.d("community $it")
                    _communityDetailData.value = it
                    Timber.d("CommunityDetail 서버 통신 성공")
                }
        }
    }

   //커뮤니티 상세 좋아요
    fun postLike() {
        viewModelScope.launch {
            likeRepository.postLike(LikeParam(postId.value ?: "", "post"))
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("CommunityDetail : 상세 좋아요 서버 통신 실패")
                }
                .collectLatest {
                    getPostDetail()
                }
                .also {
                    onLoadingEnd.value = true
                }
        }

    }


    //커뮤니티 상세 댓글 등록
    fun postCommentWrite() {
        viewModelScope.launch {
            commentRepository.postComment(
                CommentParam(
                    postId.value ?: "",
                    commentContent.value ?: ""
                )
            )
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("커뮤니티 댓글 등록 실패")
                }
                .collectLatest {
                    getPostDetail()
                    _commentData.value = it
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //댓글 삭제 서버통신
    fun deleteComment(commentId: String) {
        viewModelScope.launch {
            commentRepository.deleteComment(commentId)
                .catch {
                    Timber.d("댓글 삭제 실패")
                }
                .collectLatest {
                    _deleteComment.value = it
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }
    //원글 삭제 서버통신

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


}

