package com.nadosunbae_android.presentation.ui.review

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.sign.SelectableData
import com.nadosunbae_android.databinding.ActivityReviewDetailBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewTagBoxAdapter
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewDetailViewModel
import com.nadosunbae_android.util.CustomDialog
import com.nadosunbae_android.util.dpToPx
import com.nadosunbae_android.util.getBackgroundImage
import com.nadosunbae_android.util.showCustomDropDown


class ReviewDetailActivity :
    BaseActivity<ActivityReviewDetailBinding>(R.layout.activity_review_detail) {

    private lateinit var reviewTagBoxAdapter: ReviewTagBoxAdapter
    private var postId = NOT_POST_ID
    private var writerId: Int? = null
    private var userId: Int? = null

    private val reviewDetailViewModel: ReviewDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ReviewDetailViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTagBoxAdapter()
        initBinding()
        loadServerData()
        setClickListener()
        observeContent()

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

        // intent extra check
        if (postId != NOT_POST_ID) {
            // load review data from server
            reviewDetailViewModel.getReviewDetail(postId)
        }

        // 로그인 유저 id 불러오기
        reviewDetailViewModel.getSignUserId()

    }

    private fun setClickListener() {

        // 메뉴 버튼
        binding.btnMoreVert.setOnClickListener {

            // 로그인 유저가 해당 글 작성자일 때 -> 수정/삭제 권한
            val writerDropDownList = mutableListOf(
                SelectableData(REVIEW_EDIT, getString(R.string.review_edit), false),
                SelectableData(REVIEW_DELETE, getString(R.string.review_delete), false)
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

            // 드롭다운 메뉴 선택 시
            reviewDetailViewModel.dropDownSelected.observe(this) {
                val selected = reviewDetailViewModel.dropDownSelected.value

                if (selected != null) {

                    when (selected.id) {
                        // 수정 버튼
                        REVIEW_EDIT -> {

                        }

                        // 삭제 버튼
                        REVIEW_DELETE -> {

                            // 삭제 확인 다이얼로그
                            CustomDialog(this).genericDialog(
                                CustomDialog.DialogData(
                                    getString(R.string.alert_delete_review_title),
                                    getString(R.string.alert_delete_review_complete),
                                    getString(R.string.alert_delete_review_cancel)
                                ),
                                complete = {
                                    reviewDetailViewModel.deleteReview(postId)
                                    finish()
                                },
                                cancel = {
                                    reviewDetailViewModel.dropDownSelected.value = null
                                }
                            )

                        }
                        // 신고 버튼
                        REVIEW_REPORT -> {

                        }

                    }

                }

            }

        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnReviewLike.setOnClickListener {
            // binding.btnReviewLike.isSelected = !binding.btnReviewLike.isSelected
            reviewDetailViewModel.postLikeReview(postId)
            reviewDetailViewModel.getReviewDetail(postId)
        }

        // 선배 프로필
        binding.clReviewWriterInfo.setOnClickListener {
            val intent = Intent(this, SeniorPersonalActivity::class.java)
            val reviewData = reviewDetailViewModel.reviewDetailData.value

            if (reviewData != null)
                intent.putExtra("userId", reviewData.data.writer.writerId)

            startActivity(intent)
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeContent() {

        // 후기 내용 data
        reviewDetailViewModel.reviewDetailData.observe(this) {
            val responseValue = reviewDetailViewModel.reviewDetailData.value

            // null check
            if (responseValue != null) {
                // RecyclerView 적용
                val contentList = responseValue.data.post.contentList
                reviewTagBoxAdapter.setReviewTagBoxData(contentList)

                // Background Resource 선택
                val backgroundRes = getBackgroundImage(responseValue.data.backgroundImage.imageId)
                reviewDetailViewModel.setBackgroundRes(resources.getDrawable(backgroundRes))

                // writer
                writerId = responseValue.data.writer.writerId

                // like
                binding.btnReviewLike.isSelected = responseValue.data.like.isLiked
                binding.executePendingBindings()
            }
        }

        // 로그인된 사용자 id
        reviewDetailViewModel.signUserId.observe(this) {
            val response = reviewDetailViewModel.signUserId.value

            if (response != null) {
                userId = response
            }

        }
    }


    companion object {
        const val TAG = "ReviewDetailActivity"
        const val NOT_POST_ID = -1

        const val REVIEW_EDIT = 1
        const val REVIEW_DELETE = 2
        const val REVIEW_REPORT = 3
    }

}