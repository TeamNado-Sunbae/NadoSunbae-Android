package com.nadosunbae_android.app.presentation.ui.community

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunityDetailBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.adapter.CommunityPostDetailAdapter
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityDetailViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.closeKeyboard
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.app.util.showCustomDropDown
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
        clickBackBtn()
        infoCommentMenu()
        infoCommentMenuClick()
        reportToast()
        clickNickname()
        observeLoadingEnd()
        clickInfoPostMenu()
        floatBadUserDialog()
        clickCommentRegisterButton()
        clickDetailLike()
        setCommentObserve()
        completeDeletePost()
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


    //안꺼지게 조절
    private fun onInfo() {
        MainGlobals.infoBlock = 0
    }


    override fun onResume() {
        super.onResume()
        if (MainGlobals.infoBlock == 1) {
            finish()
        }
        communityViewModel.getPostDetail()
    }


    //상세보기 서버 통신
    private fun initInfoDetail() {
        communityViewModel.setPostId(intent.getStringExtra("postId") ?: "")
        communityPostDetailAdapter = CommunityPostDetailAdapter(
            MainGlobals.signInData?.userId ?: 0, this
        )
        binding.rcInformationDetailQuestionComment.adapter = communityPostDetailAdapter
        communityViewModel.communityDetailData
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                Timber.d("postDetail ${it.commentList}")
                binding.postDetail = it
                communityPostDetailAdapter.submitList(it.commentList)
            }
            .launchIn(lifecycleScope)
    }

    //상세보기 좋아요
    private fun clickDetailLike() {
        binding.btnInfoLike.setOnClickListener {
            communityViewModel.postLike()
        }
    }

    //댓글 등록 부분 변경
    private fun clickCommentRegisterButton() {
        binding.communityDetailViewModel = communityViewModel

        //등록 부분 색 변경
        communityViewModel.commentContent.observe(this) {
            binding.imgInformationCommentComplete.isSelected = it.isNotEmpty()
        }

        binding.imgInformationCommentComplete.setOnClickListener {
            communityViewModel.postCommentWrite()
        }
    }

    //댓글 등록 완료시에
    private fun setCommentObserve() {
        communityViewModel.commentData.flowWithLifecycle(lifecycle)
            .onEach {
                with(binding.etInformationComment) {
                    text.clear()
                    clearFocus()
                }
                this.closeKeyboard(binding.etInformationComment)

            }.launchIn(lifecycleScope)

    }

    //메뉴 펼치기
    private fun showDropDownMenu(v: View) {
        showCustomDropDown(
            communityViewModel,
            v,
            160f.dpToPx,
            null,
            -1 * 16f.dpToPx,
            null,
            false,
            communityViewModel.dropDownSelected.value!!.id,
            communityViewModel.dropDownMenu.value as MutableList<SelectableData>
        )
    }

    //원글 점 세개 메뉴 클릭
    private fun clickInfoPostMenu() {
        binding.imgCommunityDetailMenu.setOnClickListener {
            initInfoPostMenu()
        }
    }

    //원글 점 세개 메뉴
    private fun initInfoPostMenu() {
        val v = binding.imgCommunityDetailMenu
        binding.imgCommunityDetailMenu.setOnClickListener {
            communityViewModel.divisionPost.value = post
            communityViewModel.setDropDownMenu(0)
            showDropDownMenu(v)
        }
    }

    //원글 수정 이동
    private fun goUpdate() {
        val intent = Intent(this, CommunityWriteUpdateActivity::class.java)
        intent.apply {
            putExtra("updateData", communityViewModel.updateData.value)
        }
        startActivity(intent)
    }


    //답글 점 세개 메뉴 클릭시 나오는 다이얼로그
    private fun infoCommentMenu() {
        communityPostDetailAdapter.setItemClickListener { v, position, user, commentId ->
            with(communityViewModel) {
                setDropDownMenu(user)
                this.commentId.value = commentId
                this.position.value = position
                divisionPost.value = comment
            }
            showDropDownMenu(v)
        }
    }


    //답글 메뉴 신고, 삭제 클릭시 이벤트
    private fun infoCommentMenuClick() {
        communityViewModel.dropDownSelected.observe(this) {
            val divisionPost = communityViewModel.divisionPost.value ?: false
            val position = communityViewModel.position.value ?: 0
            val commentId = communityViewModel.commentId.value ?: 0
            when (it.name) {
                resources.getString(R.string.question_detail_delete) ->
                    deleteDialog(
                        divisionPost,
                        setCheckMenu = {
                            if (divisionPost == comment) {
                                communityPostDetailAdapter.setCheckMenu(
                                    delete,
                                    position
                                )
                            }
                        },
                        deleteComment = {
                            communityViewModel.deleteComment(commentId.toString())
                        },
                        deleteWrite = {
                            communityViewModel.deletePost()
                        },
                    )
                resources.getString(R.string.question_detail_report) -> floatReportReasonDialog()
                resources.getString(R.string.question_detail_update) -> goUpdate()
            }
        }
    }


    // 게시글 or 답글 삭제
    private fun deleteDialog(
        divisionPost: Boolean,
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
                if (divisionPost == comment) {
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

    //원글 삭제시 상세 화면 종료
    private fun completeDeletePost() {
        communityViewModel.deletePostData.flowWithLifecycle(lifecycle)
            .onEach {
                if (it.isDeleted) finish()
            }
            .launchIn(lifecycleScope)
    }

    //신고 사유 다이얼로그 띄우기
    private fun floatReportReasonDialog() {
        val dialog = CustomDialog(this)
        dialog.reportDialog()
        dialog.setReportClickListener(
            object : CustomDialog.ReportClickListener {
                override fun reportClick(text: String) {
                    communityViewModel.reportReasonInfo.value = text
                    reportDialog()
                }
            }
        )
    }


    //신고 다이얼로그 띄우기
    private fun reportDialog() {
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                resources.getString(R.string.request_report),
                resources.getString(R.string.agree_report),
                resources.getString(R.string.disagree_report)
            ),
            complete = {
                communityViewModel.postReport()
            },
            cancel = {

            }
        )

    }

    //신고하기 토스트 띄우기
    private fun reportToast() {
        communityViewModel.reportStatus.observe(this) {
            if (it == 201) {
                Toast.makeText(this, "신고가 접수되었습니다", Toast.LENGTH_SHORT).show()
            } else if (it == 409) {
                Toast.makeText(this, "이미 신고한 글입니다.", Toast.LENGTH_SHORT).show()
            }
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
            val writerId = communityViewModel.communityDetailData.value.writerId
            val userId = MainGlobals.signInData?.userId ?: 0
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
        const val post = true
        const val comment = false
    }

}