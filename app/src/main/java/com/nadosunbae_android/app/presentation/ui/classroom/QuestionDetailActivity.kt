package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityQuestionDetailBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomQuestionDetailAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.QuestionDetailViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.classroom.CommentUpdateItem
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.domain.model.classroom.QuestionDetailData
import com.nadosunbae_android.domain.model.classroom.ReportItem
import com.nadosunbae_android.domain.model.like.LikeParam
import com.nadosunbae_android.domain.model.main.SelectableData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class QuestionDetailActivity :
    BaseActivity<ActivityQuestionDetailBinding>(R.layout.activity_question_detail) {
    private val questionDetailViewModel: QuestionDetailViewModel by viewModels()

    private lateinit var classRoomQuestionDetailAdapter: ClassRoomQuestionDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initQuestionOneToOneMenu()
        initQuestionDetail()
        questionDetailLike()
        backBtn()
        questionOneToOneMenu()
        checkMenuName()
        updateComment()
        getReportReason()
        reportToast()
        onQuestion()
        observeComment()
        observeLoadingEnd()
        changeRegisterBtn()
    }


    //로딩 종료
    private fun observeLoadingEnd() {
        questionDetailViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }


    //종료 금지
    private fun onQuestion() {
        MainGlobals.infoBlock = 0

    }


    override fun onResume() {
        super.onResume()
        if (MainGlobals.infoBlock == 1) {
            finish()
        }
        questionDetailViewModel.getClassRoomQuestionDetail(
            questionDetailViewModel.postId.value ?: 0
        )
    }

    // all -> 1:1 질문 인지 전체 질문인지 구분
    // 전체 질문 상세보기
    private fun initQuestionDetail() {
        val postId = intent.getIntExtra("postId", 0)
        questionDetailViewModel.postId.value = postId
        registerComment()
        val userId = MainGlobals.signInData?.userId ?: -1
        val all = intent.getIntExtra("all", 0)
        val myPageNum = intent.getIntExtra("myPageNum", 0)


        showLoading()
        questionDetailViewModel.getClassRoomQuestionDetail(postId)
        classRoomQuestionDetailAdapter = ClassRoomQuestionDetailAdapter(this, userId)
        binding.rcQuestionDetail.adapter = classRoomQuestionDetailAdapter

        questionDetailViewModel.questionDetailData.observe(this) {


            with(classRoomQuestionDetailAdapter) {
                Timber.d("questionDetailUser: ${it.answererId}, ${it.questionerId}")
                Timber.d("questionDetailUserWriter : ${it.messageList}")
                setViewTitle(all, postId)
                setQuestionDetailUser(it)
                setLike(it.likeCount, it.isLiked)
                setQuestionDetail(it.messageList as MutableList<QuestionDetailData.Message>)

                Timber.d("asdfasdf $myPageNum $all $userId $it")
                //1:1질문 타인 글 쓰는거 막기
//                if (myPageNum != 1 && all != 1 && userId != it.questionerId && userId != it.answererId) {
                if (userId != it.questionerId && userId != it.answererId) {
                    binding.etQuestionComment.isEnabled = false
                    binding.etQuestionComment.hint = getString(R.string.text_comment_impossible)
                }
            }

        }

        //전체질문 1:1질문 구분
        val myPageDivisionNum = intent.getIntExtra("postTypeId", -1)
        if (all == 1 || myPageDivisionNum == 3) {
            binding.textQuestionDetailTitle.text = "질문"
        } else {
            binding.textQuestionDetailTitle.text = "1:1질문"
        }
    }

    //전체 질문 상세보기 좋아요
    private fun questionDetailLike() {
        classRoomQuestionDetailAdapter.setItemLikeClickListener {
            questionDetailViewModel.postClassRoomLike()
        }
    }

    //답글 작성 중 종이비행기 색상 변경
    private fun changeRegisterBtn() {
        binding.etQuestionComment.addTextChangedListener {
            binding.imgQuestionCommentComplete.isSelected = !it.isNullOrEmpty()
        }
    }


    // 전체 질문 상세 댓글 등록
    private fun registerComment() {
        binding.imgQuestionCommentComplete.setOnClickListener {
            questionDetailViewModel.postQuestionCommentWrite(
                QuestionCommentWriteItem(
                    questionDetailViewModel.postId.value?.toInt() ?: -1, binding.etQuestionComment.text.toString()
                )
            )
            binding.etQuestionComment.setText("")
        }

        questionDetailViewModel.registerComment.observe(this) {
            if (it.success) {
                showLoading()
                questionDetailViewModel.getClassRoomQuestionDetail(questionDetailViewModel.postId.value?.toInt() ?: -1)
            }
        }
    }

    private fun initQuestionOneToOneMenu() {
        questionDetailViewModel.dropDownSelected.value = SelectableData(3, "테스트", false)
    }


    // 1:1 질문 점 세개 메뉴 분기처리 (user : 1 -> 질문자, 2 -> 답변자, 3 -> 질문자 재답변) 나머지는 제 3자
    // viewNum : 1 -> 질문자 뷰, 2 -> 답변자 뷰
    private fun questionOneToOneMenu() {
        classRoomQuestionDetailAdapter.setItemClickListener(
            object : ClassRoomQuestionDetailAdapter.OnItemClickListener {
                override fun onClick(
                    v: View,
                    position: Int,
                    user: Int,
                    viewNum: Int,
                    commentId: Int,
                    deleteNum: Int
                ) {
                    Timber.d("oneToOneNum: $user+$viewNum+$commentId")
                    questionDetailViewModel.commentId.value = commentId
                    questionDetailViewModel.position.value = position
                    questionDetailViewModel.viewNum.value = viewNum
                    questionDetailViewModel.deleteNum.value = deleteNum
                    if ((user == 1 && viewNum == 1) or (user == 2 && viewNum == 2)) {
                        val dropDown = mutableListOf<SelectableData>(
                            SelectableData(
                                1,
                                resources.getString(R.string.question_detail_update),
                                false
                            ),
                            SelectableData(
                                2,
                                resources.getString(R.string.question_detail_delete),
                                false
                            )
                        )
                        showCustomDropDown(
                            questionDetailViewModel,
                            v,
                            160f.dpToPx,
                            null,
                            -1 * 16f.dpToPx,
                            null,
                            false,
                            questionDetailViewModel.dropDownSelected.value!!.id,
                            dropDown
                        )
                    } else if ((user == 1 && viewNum == 2) or (user == 3)) {
                        val dropDown = mutableListOf<SelectableData>(
                            SelectableData(
                                1,
                                resources.getString(R.string.question_detail_report),
                                false
                            ),
                        )
                        showCustomDropDown(
                            questionDetailViewModel,
                            v,
                            160f.dpToPx,
                            null,
                            -1 * 16f.dpToPx,
                            null,
                            false,
                            questionDetailViewModel.dropDownSelected.value!!.id,
                            dropDown
                        )
                    } else if (user == 2 && viewNum == 1) {
                        val dropDown = mutableListOf<SelectableData>(
                            SelectableData(
                                1,
                                resources.getString(R.string.question_detail_report),
                                true
                            ),
                            SelectableData(
                                2,
                                resources.getString(R.string.question_detail_delete),
                                false
                            )
                        )
                        showCustomDropDown(
                            questionDetailViewModel,
                            v,
                            160f.dpToPx,
                            null,
                            -1 * 16f.dpToPx,
                            null,
                            false,
                            questionDetailViewModel.dropDownSelected.value!!.id,
                            dropDown
                        )
                    }

                }
            })
    }

    // 메세지 수정 서버  통신
    private fun updateComment() {
        classRoomQuestionDetailAdapter.setUpdateListener(
            object : ClassRoomQuestionDetailAdapter.UpdateListener {
                override fun onUpdate(content: String, commentId: Int) {
                    showLoading()
                    questionDetailViewModel.putCommentUpdate(commentId, content)
                }
            }
        )

    }


    //어떤 메뉴 선택했는지 확인
    private fun checkMenuName() {
        questionDetailViewModel.dropDownSelected.observe(this) {
            val viewNum = questionDetailViewModel.viewNum.value ?: 0
            val position = questionDetailViewModel.position.value ?: 0
            val deleteNum = questionDetailViewModel.deleteNum.value ?: 0
            when (it.name) {
                resources.getString(R.string.question_detail_update) ->
                    classRoomQuestionDetailAdapter.setCheckMenu(update, viewNum, position)
                resources.getString(R.string.question_detail_report) ->
                    classRoomQuestionDetailAdapter.setCheckMenu(report, viewNum, position)
                resources.getString(R.string.question_detail_delete) ->
                    deleteDialog(deleteNum,
                        setCheckMenu = {
                            classRoomQuestionDetailAdapter.setCheckMenu(
                                delete,
                                viewNum,
                                position
                            )
                        },
                        deleteComment = {
                            questionDetailViewModel.deleteComment(
                                questionDetailViewModel.commentId.value ?: 0
                            )
                        },
                        deleteWrite = {
                            questionDetailViewModel.deletePost(
                                questionDetailViewModel.postId.value ?: 0
                            )
                        }
                    )

            }
        }
    }

    private fun observeComment() {
        questionDetailViewModel.postComment.observe(this) {
            questionDetailViewModel.getClassRoomQuestionDetail(
                questionDetailViewModel.postId.value ?: 0
            )
        }
        questionDetailViewModel.commentUpdate.observe( this) {
            questionDetailViewModel.getClassRoomQuestionDetail(
                questionDetailViewModel.postId.value ?: 0
            )
        }
        questionDetailViewModel.deletePostData.observe(this) {
            questionDetailViewModel.getClassRoomQuestionDetail(
                questionDetailViewModel.postId.value ?: 0
            )
        }
    }

    //삭제 부분 다이얼로그 띄우기
    private fun deleteDialog(
        deleteNum: Int,
        setCheckMenu: () -> Unit,
        deleteComment: () -> Unit,
        deleteWrite: () -> Unit
    ) {

        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                resources.getString(R.string.alert_delete_review_title),
                resources.getString(R.string.alert_delete_review_complete),
                resources.getString(R.string.alert_delete_review_cancel)
            ),
            complete = {
                setCheckMenu()
                if (deleteNum == 1) {
                    deleteComment()
                } else {
                    deleteWrite()
                }

            },
            cancel = {

            }
        )
    }


    //신고 사유 받아오기
    private fun getReportReason() {
        classRoomQuestionDetailAdapter.setReportListener(
            object : ClassRoomQuestionDetailAdapter.ReportListener {
                override fun onReport(text: String, divisionNum: Int) {
                    questionDetailViewModel.reportReason.value = text
                    reportDialog(divisionNum)
                }
            }
        )

    }

    //신고 다이얼로그 띄우기
    private fun reportDialog(divisionNum: Int) {
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                resources.getString(R.string.request_report),
                resources.getString(R.string.agree_report),
                resources.getString(R.string.disagree_report)
            ),
            complete = {
                when (divisionNum) {
                    2 -> questionDetailViewModel.postReport(
                        ReportItem(
                            questionDetailViewModel.postId.value ?: 0,
                            divisionNum,
                            questionDetailViewModel.reportReason.value ?: ""
                        )
                    )
                    3 -> questionDetailViewModel.postReport(
                        ReportItem(
                            questionDetailViewModel.commentId.value ?: 0,
                            divisionNum,
                            questionDetailViewModel.reportReason.value ?: ""
                        )
                    )
                }
            },
            cancel = {

            }
        )

    }

    //신고하기 토스트 띄우기
    private fun reportToast() {
        questionDetailViewModel.reportStatus.observe(this) {
            if (it == 200) {
                Toast.makeText(this, "신고가 접수되었습니다", Toast.LENGTH_SHORT).show()
            } else if (it == 409) {
                Toast.makeText(this, "이미 신고한 글입니다.", Toast.LENGTH_SHORT).show()
            }

        }

    }


    //뒤로가기
    private fun backBtn() {
        binding.imgQuestionDetailTitle.setOnClickListener {
            finish()
        }
    }


    companion object {
        const val update = 1
        const val report = 2
        const val delete = 3

    }
}