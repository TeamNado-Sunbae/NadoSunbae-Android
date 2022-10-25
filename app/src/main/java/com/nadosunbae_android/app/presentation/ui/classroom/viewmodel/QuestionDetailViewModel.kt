package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.app.util.ResultWrapper
import com.nadosunbae_android.app.util.safeApiCall
import com.nadosunbae_android.domain.model.classroom.*
import com.nadosunbae_android.domain.model.comment.CommentData
import com.nadosunbae_android.domain.model.comment.CommentParam
import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeParam
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.post.PostWriteParam
import com.nadosunbae_android.domain.repository.comment.CommentRepository
import com.nadosunbae_android.domain.repository.like.LikeRepository
import com.nadosunbae_android.domain.repository.post.PostRepository
import com.nadosunbae_android.domain.usecase.classroom.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuestionDetailViewModel @Inject constructor(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
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

    private val _postComment = MutableLiveData<CommentData>()
    val postComment: LiveData<CommentData>
        get() = _postComment




    //전체 질문 상세보기 서버 통신
    fun getClassRoomQuestionDetail(postId: Int) {
        viewModelScope.launch {
            postRepository.getPostDetail(postId.toString())
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("classRoomDetail : 메인 서버 통신 실패!")
                }
                .collectLatest {
                    val messageList = mutableListOf<QuestionDetailData.Message>()
                    messageList.add(
                        QuestionDetailData.Message(
                            content = it.content,
                            createdAt = it.createdAt,
                            isDeleted = false,
                            messageId = it.postId,
                            title = it.title,
                            firstMajorName = it.firstMajorName,
                            firstMajorStart = it.firstMajorStart,
                            isQuestioner = true,
                            nickname = it.nickname,
                            profileImageId = it.profileImageId,
                            secondMajorName = it.secondMajorName,
                            secondMajorStart = it.secondMajorStart,
                            writerId = it.writerId
                        )
                    )
                    messageList.addAll(
                        it.commentList.map { msg ->
                            QuestionDetailData.Message(
                                content = msg.content,
                                createdAt = msg.createdAt,
                                isDeleted = msg.isDeleted,
                                messageId = msg.commentId,
                                title = msg.content,
                                firstMajorStart = msg.firstMajorStart,
                                firstMajorName = msg.firstMajorName,
                                isQuestioner = msg.isPostWriter,
                                nickname = msg.nickname,
                                profileImageId = msg.profileImageId,
                                secondMajorStart = msg.secondMajorStart,
                                secondMajorName = msg.secondMajorName,
                                writerId = msg.commentWriterId
                            )
                        }
                    )

                    _questionDetailData.value = QuestionDetailData(
                        answererId = it.writerId,
                        isLiked = it.isLiked,
                        likeCount = it.likeCount,
                        messageList = messageList,
                        questionerId = 0
                    )
                    Timber.d("classRoomDetail : 메인 서버 통신 성공! ${_questionDetailData.value}")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    // 질문 좋아요 및 좋아요 취소 서버 통신
    fun postClassRoomLike() {
        viewModelScope.launch {
            likeRepository.postLike(
                LikeParam(
                    postId.value.toString(),
                    "post"
                ),
            )
                .catch {
                    Timber.d("classRoomPostLike : 좋아요 서버 통신 실패!")
                }
                .collectLatest {
                    getClassRoomQuestionDetail(postId.value ?: 0)
                }.also {
                    onLoadingEnd.value = true
                }
        }

    }


    //댓글 등록 서버 통신
    fun postQuestionCommentWrite(questionCommentWriteItem: QuestionCommentWriteItem) {
        viewModelScope.launch {
            commentRepository.postComment(
                CommentParam(
                    postId = questionCommentWriteItem.postId.toString(),
                    content = questionCommentWriteItem.content
                ))
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("questionComment : 댓글 통신 실패!")
                }
                .collectLatest {
                    _postComment.value = it
                    Timber.d("questionComment : 댓글 통신 성공!")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //댓글 수정 서버통신
    fun putCommentUpdate(commentId: Int, content: String) {
        viewModelScope.launch {
            commentRepository.putComment(commentId.toString(), content)
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    it.printStackTrace()
                    Timber.d("commentUpdate : 댓글 수정 실패!")
                }
                .collectLatest {
                    _commentUpdate.value = it
                    Timber.d("commentUpdate : 댓글 수정 성공!")
                }
                .also {
                    onLoadingEnd.value = true
                }

        }
    }

    //댓글 삭제 서버통신
    fun deleteComment(commentId: Int) {
        viewModelScope.launch {
            commentRepository.deleteComment(commentId.toString())
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    it.printStackTrace()
                    Timber.d("deleteComment : 댓글 삭제 실패!")
                }
                .collectLatest {
                    _deleteData.value = DeleteCommentData(
                        commentId = it.commentId,
                        isDeleted = it.isDeleted
                    )
                    Timber.d("deleteComment : 댓글 삭제 성공!")
                }
                .also {
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



