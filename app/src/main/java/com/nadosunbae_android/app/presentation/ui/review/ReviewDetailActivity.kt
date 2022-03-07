package com.nadosunbae_android.app.presentation.ui.review

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityReviewDetailBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.review.ReviewWriteActivity.Companion.MODE_MODIFY
import com.nadosunbae_android.app.presentation.ui.review.adapter.ReviewTagBoxAdapter
import com.nadosunbae_android.app.presentation.ui.review.viewmodel.ReviewDetailViewModel
import com.nadosunbae_android.app.util.*
import com.nadosunbae_android.domain.model.classroom.ReportItem
import com.nadosunbae_android.domain.model.main.SelectableData
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReviewDetailActivity :
    BaseActivity<ActivityReviewDetailBinding>(R.layout.activity_review_detail) {

    private lateinit var reviewTagBoxAdapter: ReviewTagBoxAdapter
    // 후기글 id
    private var postId = NOT_POST_ID
    // 후기글 작성자 id
    private var writerId: Int? = null
    // 로그인 유저 id
    private var userId: Int? = null


    private val reviewDetailViewModel: ReviewDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTagBoxAdapter()
        initBinding()
        setClickListener()
        observeContent()
        observeLoadingEnd()
        observeDropDown()
        observeReportResult()

    }

    override fun onResume() {
        super.onResume()

        loadServerData()
    }


    private fun initTagBoxAdapter() {
        reviewTagBoxAdapter = ReviewTagBoxAdapter(this)
        binding.rvReviewDetail.adapter = reviewTagBoxAdapter
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.reviewDetailViewModel = reviewDetailViewModel
    }

    private fun loadServerData() {
        postId = intent.getIntExtra("postId", NOT_POST_ID)
        userId = intent.getIntExtra("userId", 0)

        // intent extra check
        if (postId != NOT_POST_ID) {

            showLoading()
            // load review data from server
            reviewDetailViewModel.getReviewDetail(postId)

            lookMyPost()
        }

    }

    // 자신의 글이면 질문 가능여부 표시 x
    private fun lookMyPost() {
        if (userId == writerId)
            binding.tvOnQuestion.visibility = View.VISIBLE
        else
            binding.tvOnQuestion.visibility = View.INVISIBLE
    }

    private fun setClickListener() {

        // 메뉴 버튼
        binding.btnMoreVert.setOnClickListener {
            runMenuAction()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        // 좋아요 버튼
        binding.btnReviewLike.setOnClickListener {
            showLoading()
            reviewDetailViewModel.postLikeReview(postId)
        }

        // 선배 프로필
        binding.clReviewWriterInfo.setOnClickListener {

            if (userId == writerId) {       // 자신의 마이페이지로 이동

                finish()
            } else {                        // 해당 선배의 페이지로 이동
                val intent = Intent(this, SeniorPersonalActivity::class.java).apply {
                    putExtra("userId", writerId)
                }
                startActivity(intent)
            }
        }
    }

    private fun observeDropDown() {

        // 드롭다운 메뉴 선택 시
        reviewDetailViewModel.dropDownSelected.observe(this) {
            val selected = reviewDetailViewModel.dropDownSelected.value

            if (selected != null) {

                when (selected.id) {
                    REVIEW_EDIT -> editReview()
                    REVIEW_DELETE -> deleteReview()
                    REVIEW_REPORT -> reportReview()
                }
                // 선택된 드롭다운 다시 취소
                reviewDetailViewModel.dropDownSelected.value = null
            }


        }
    }

    // 메뉴 버튼 동작
    private fun runMenuAction() {

        // 로그인 유저가 해당 글 작성자일 때 -> 수정/삭제 권한
        val writerDropDownList = mutableListOf(
            SelectableData(REVIEW_EDIT, getString(R.string.review_edit), false),
            SelectableData(REVIEW_DELETE, getString(R.string.review_delete), false),
        )

        // 다른 유저의 글일 때 -> 신고만 가능
        val reportDropDownList = mutableListOf(
            SelectableData(REVIEW_REPORT, getString(R.string.review_report), false)
        )
        var dropDownList = reportDropDownList

        // 로그인 유저가 해당 글 작성자 (수정/삭제 권한)
        if (writerId == userId)
            dropDownList = writerDropDownList

        showCustomDropDown(reviewDetailViewModel, binding.btnMoreVert, 160f.dpToPx, 0, dropDownList)

    }

    // 후기 수정
    private fun editReview() {
        val intent = Intent(this, ReviewWriteActivity::class.java)
        val responseData = reviewDetailViewModel.reviewDetailData.value

        // null check
        if (responseData != null) {

            intent.putExtra("mode", MODE_MODIFY)
            // 후기 수정을 위해 기존 데이터를 넘겨줌
            intent.putExtra("modifyData", responseData)
            startActivity(intent)
        }
    }

    // 후기 삭제
    private fun deleteReview() {
        // 삭제 확인 다이얼로그
        CustomDialog(this).genericDialog(
            CustomDialog.DialogData(
                getString(R.string.alert_delete_review_title),
                getString(R.string.alert_delete_review_complete),
                getString(R.string.alert_delete_review_cancel)
            ),
            complete = {
                showLoading()
                reviewDetailViewModel.deleteReview(postId)
                finish()
            },
            cancel = {
            }
        )
    }

    // 후기 신고
    private fun reportReview() {
        // 신고 사유 고르는 다이얼로그
        CustomDialog(this).reportDialog()
            .setReportClickAction {

                // 신고할지 확인하는 알럿
                CustomDialog(this).genericDialog(
                    dialogText = CustomDialog.DialogData(
                        title = getString(R.string.request_report),
                        complete = getString(R.string.agree_report),
                        cancel = getString(R.string.disagree_report)
                    ),
                    complete = {
                       requestReportReview(it)  // 신고 진행
                    },
                    cancel = {
                    }
                )
            }
    }

    // viewModel을 통해 서버에 신고 요청
    private fun requestReportReview(reason: String) {
        reviewDetailViewModel.postReport(
            ReportItem(
                reportedTargetId = postId,
                reportedTargetTypeId = REPORT_TYPE_REVIEW,
                reason = reason
            )
        )
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeContent() {

        // 후기 내용 data
        reviewDetailViewModel.reviewDetailData.observe(this) {
            val reviewDetail = reviewDetailViewModel.reviewDetailData.value

            // null check
            if (reviewDetail != null) {

                // RecyclerView 적용
                val contentList = reviewDetail.contentList
                reviewTagBoxAdapter.setReviewTagBoxData(contentList)

                // Background Resource 선택
                val backgroundRes = getBackgroundImage(reviewDetail.backgroundImageId)
                reviewDetailViewModel.setBackgroundRes(resources.getDrawable(backgroundRes))

                // writer
                writerId = reviewDetail.writerId

                binding.executePendingBindings()
            }
        }

    }

    private fun observeLoadingEnd() {
        reviewDetailViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }

    private fun observeReportResult() {
        reviewDetailViewModel.reportSuccess.observe(this) {
            val msg = if (it)
                getString(R.string.report_success)       // 신고 성공
            else
                getString(R.string.report_fail)         // 신고 실패
            shortToast(msg)
        }
    }


    companion object {
        const val TAG = "ReviewDetailActivity"
        const val NOT_POST_ID = -1

        const val REVIEW_EDIT = 1
        const val REVIEW_DELETE = 2
        const val REVIEW_REPORT = 3

        const val REPORT_TYPE_REVIEW = 1
    }

}