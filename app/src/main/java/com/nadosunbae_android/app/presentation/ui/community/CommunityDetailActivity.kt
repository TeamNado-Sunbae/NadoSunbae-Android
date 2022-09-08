package com.nadosunbae_android.app.presentation.ui.community

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunityDetailBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionWriteActivity
import com.nadosunbae_android.app.presentation.ui.community.adapter.CommunityPostDetailAdapter
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityDetailViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.domain.model.classroom.ReportItem
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.model.main.SelectableData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class CommunityDetailActivity :
    BaseActivity<ActivityCommunityDetailBinding>(R.layout.activity_community_detail) {
    private val communityViewModel: CommunityDetailViewModel by viewModels()
    private lateinit var communityPostDetailAdapter: CommunityPostDetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInfo()
        initInfoDetail()
        infoLike()
        clickBackBtn()
        initInfoCommentMenu()
        infoCommentMenu()
        infoCommentMenuClick()
        reportToast()
        clickNickname()
        observeLoadingEnd()
        clickInfoPostMenu()
        floatBadUserDialog()
        changeRegisterBtn()
    }

    //로딩 종료
    private fun observeLoadingEnd() {
        communityViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }

    //부적절 사용자 다이얼로그 띄우기
    private fun floatBadUserDialog() {
        if (MainGlobals.signInData!!.isUserReported || MainGlobals.signInData!!.isReviewInappropriate) {
            CustomDialog(this).genericDialog(
                CustomDialog.DialogData(
                    MainGlobals.signInData?.message.toString(),
                    resources.getString(R.string.sign_in_question),
                    resources.getString(R.string.email_certification_close)
                ),
                complete = {
                    var intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.question_kakao)))
                    startActivity(intent)
                },
                cancel = { finish() }
            )
        }
    }

    //답글 작성 중 종이비행기 색상 변경
    private fun changeRegisterBtn() {
        binding.etInformationComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.imgInformationCommentComplete.isSelected = !s.isNullOrEmpty()
            }
        })
    }


    //안꺼지게 조절
    private fun onInfo() {
        MainGlobals.infoBlock = 0
    }


    override fun onResume() {
        super.onResume()
        if (MainGlobals.infoBlock == 1) {
            finish()
        }
        communityViewModel.getPostDetail(communityViewModel.postId.value ?: "")
    }


    //정보 상세보기 서버 통신
    private fun initInfoDetail() {
        communityViewModel.setPostId(intent.getStringExtra("postId") ?: "")
        communityViewModel.getPostDetail(communityViewModel.postId.value ?: "")
        //Todo 유저 아이디 넣기
        communityPostDetailAdapter = CommunityPostDetailAdapter(0, this)
        communityViewModel.communityDetailData
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                binding.postDetail = it
                communityPostDetailAdapter.submitList(it.commentList)
            }
            .launchIn(lifecycleScope)
    }

    //원글 점 세개 메뉴 클릭
    private fun clickInfoPostMenu() {
        binding.imgCommunityDetailMenu.setOnClickListener {
            initInfoPostMenu(
                communityViewModel.userId.value ?: 0,
                communityViewModel.writerId.value ?: 0
            )
        }
    }

    //원글 점 세개 메뉴
    private fun initInfoPostMenu(userId: Int, writerId: Int) {
        val v = binding.imgCommunityDetailMenu
        communityViewModel.divisionPost.value = post
        binding.imgCommunityDetailMenu.setOnClickListener {
            if (userId == writerId) {
                val dropDown = mutableListOf<SelectableData>(
                    SelectableData(1, resources.getString(R.string.question_detail_update), false),
                    SelectableData(2, resources.getString(R.string.question_detail_delete), false),
                )
                showCustomDropDown(
                    communityViewModel,
                    v,
                    160f.dpToPx,
                    null,
                    -1 * 16f.dpToPx,
                    null,
                    false,
                    communityViewModel.dropDownSelected.value!!.id,
                    dropDown
                )
            } else {
                val dropDown = mutableListOf<SelectableData>(
                    SelectableData(2, resources.getString(R.string.question_detail_report), false)
                )
                showCustomDropDown(
                    communityViewModel,
                    v,
                    160f.dpToPx,
                    null,
                    -1 * 16f.dpToPx,
                    null,
                    false,
                    communityViewModel.dropDownSelected.value!!.id,
                    dropDown
                )
            }
        }
    }

    //점 세개 메뉴 다이얼로그 초기화
    private fun initInfoCommentMenu() {
        communityViewModel.dropDownSelected.value = SelectableData(3, "테스트", false)
    }

    //답글 점 세개 메뉴 클릭시 나오는 다이얼로그
    private fun infoCommentMenu() {
        communityPostDetailAdapter.setItemClickListener { v, position, user, commentId ->
            communityViewModel.commentId.value = commentId
            communityViewModel.position.value = position
            communityViewModel.divisionPost.value = comment
            if (user == 1) {
                val dropDown = mutableListOf<SelectableData>(
                    SelectableData(
                        1,
                        resources.getString(R.string.question_detail_delete),
                        false
                    )
                )
                showCustomDropDown(
                    communityViewModel,
                    v,
                    160f.dpToPx,
                    null,
                    -1 * 16f.dpToPx,
                    null,
                    false,
                    communityViewModel.dropDownSelected.value!!.id,
                    dropDown
                )
            } else {
                val dropDown = mutableListOf<SelectableData>(
                    SelectableData(
                        2,
                        resources.getString(R.string.question_detail_report),
                        false
                    )
                )
                showCustomDropDown(
                    communityViewModel,
                    v,
                    160f.dpToPx,
                    null,
                    -1 * 16f.dpToPx,
                    null,
                    false,
                    communityViewModel.dropDownSelected.value!!.id,
                    dropDown
                )
            }
        }
    }

    //답글 메뉴 신고, 삭제 클릭시 이벤트
    private fun infoCommentMenuClick() {
        communityViewModel.dropDownSelected.observe(this) {
            val divisionPost = communityViewModel.divisionPost.value ?: 0
            val position = communityViewModel.position.value ?: 0
            val commentId = communityViewModel.commentId.value ?: 0
            val postId = communityViewModel.postId.value ?: ""
            when (it.name) {
                resources.getString(R.string.question_detail_delete) ->
                    deleteDialog(
                        divisionPost,
                        setCheckMenu = {
                            if (divisionPost == 3) {
                                communityPostDetailAdapter.setCheckMenu(
                                    delete,
                                    position
                                )
                            }
                        },
                        deleteComment = { communityViewModel.deleteComment(commentId) },
                        deleteWrite = { communityViewModel.deletePost(0) },
                    )
                resources.getString(R.string.question_detail_report) -> floatReportReasonDialog()
                resources.getString(R.string.question_detail_update) -> goUpdate()
            }
        }
    }

    //원글 수정 이동
    private fun goUpdate() {
        val intent = Intent(this, QuestionWriteActivity::class.java)
        intent.apply {
            putExtra("writerUpdateTitle", binding.textCommunityDetailQuestionTitle.text)
            putExtra("writerUpdateContent", binding.textCommunityDetailQuestionContent.text)
            putExtra("division", 1)
            putExtra("postId", communityViewModel.postId.value)
            putExtra("title", "정보글 작성")
        }
        startActivity(intent)
    }


    //정보 답글 삭제
    private fun deleteDialog(
        divisionPost: Int,
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
                if (divisionPost == 3) {
                    deleteComment()
                } else {
                    deleteWrite()
                    finish()
                }
                setCheckMenu()
            },
            cancel = {}
        )
    }

    //신고 사유 다이얼로그 띄우기
    private fun floatReportReasonDialog() {
        val divisionPost = communityViewModel.divisionPost.value ?: 0
        val dialog = CustomDialog(this)
        dialog.reportDialog()
        dialog.setReportClickListener(
            object : CustomDialog.ReportClickListener {
                override fun reportClick(text: String) {
                    communityViewModel.reportReasonInfo.value = text
                    reportDialog(divisionPost)
                }
            }
        )


    }


    //신고 다이얼로그 띄우기
    private fun reportDialog(divisionPost: Int) {
        val commentId = communityViewModel.commentId.value ?: 0
        val reportReason = communityViewModel.reportReasonInfo.value ?: ""
        val postId = communityViewModel.postId.value ?: 0
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                resources.getString(R.string.request_report),
                resources.getString(R.string.agree_report),
                resources.getString(R.string.disagree_report)
            ),
            complete = {
                when (divisionPost) {
                    comment -> communityViewModel.postReport(
                        ReportItem(
                            commentId,
                            comment,
                            reportReason
                        )
                    )
                    post -> communityViewModel.postReport(
                        ReportItem(
                            0,
                            post,
                            reportReason
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
        communityViewModel.reportStatusInfo.observe(this) {
            if (it == 200) {
                Toast.makeText(this, "신고가 접수되었습니다", Toast.LENGTH_SHORT).show()
            } else if (it == 409) {
                Toast.makeText(this, "이미 신고한 글입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //정보 상세보기 댓글 달기
    private fun registerComment(postId: String) {
        communityViewModel.postInfoCommentWrite(
            QuestionCommentWriteItem(
                0, binding.etInformationComment.text.toString()
            )
        )

        communityViewModel.registerInfoComment.observe(this) {
            if (it.success) {
                communityViewModel.getPostDetail(postId)
            }
        }
    }

    //정보 좋아요 서버 통신
    private fun infoLike() {
        binding.btnInfoLike.setOnClickListener {
            val likePostId = communityViewModel.postId.value ?: ""
            communityViewModel.postClassRoomInfoLike(LikeItem(0, 2))
            showLoading()
            communityViewModel.getPostDetail(likePostId)
        }
    }

    //정보 뒤로가기
    private fun clickBackBtn() {
        binding.imgCommunityDetailTitleBack.setOnClickListener {
            finish()
        }
    }

    //클릭시 마이페이지 또는 선배 개인페이지 이동
    private fun clickNickname() {

        binding.textCommunityDetailQuestionName.setOnClickListener {
            val writerId = communityViewModel.writerId.value ?: 0
            val userId = communityViewModel.userId.value ?: 0
            Timber.d("userId : $userId, writerId : $writerId")
            var fragmentNum = -1
            var bottomNavItem = -1
            var blockDivision = -1

            if (userId == writerId) {
                fragmentNum = 6
                bottomNavItem = 5
            } else {
                fragmentNum = 4
                bottomNavItem = 2
                blockDivision = 1
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.apply {
                putExtra("fragmentNum", fragmentNum)
                putExtra("bottomNavItem", bottomNavItem)
                putExtra("signData", MainGlobals.signInData)
                putExtra("loading", false)
                putExtra("seniorId", writerId)
                putExtra("blockDivision", blockDivision)
            }
            startActivity(intent)
        }
    }


    companion object {
        const val update = 1
        const val report = 2
        const val delete = 3


        //원글 댓글 분류
        const val post = 2
        const val comment = 3
    }

}