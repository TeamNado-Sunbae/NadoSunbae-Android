package com.nadosunbae_android.presentation.ui.review

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.ActivityReviewDetailBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewTagBoxAdapter
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewDetailViewModel
import com.nadosunbae_android.util.getBackgroundImage


class ReviewDetailActivity :
    BaseActivity<ActivityReviewDetailBinding>(R.layout.activity_review_detail) {

    private lateinit var reviewTagBoxAdapter: ReviewTagBoxAdapter
    private var postId = NOT_POST_ID

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
        getServerData()
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

    private fun getServerData() {
        postId = intent.getIntExtra("postId", NOT_POST_ID)

        // intent extra check
        if (postId != NOT_POST_ID) {
            reviewDetailViewModel.getReviewDetail(postId)
        }

    }

    private fun setClickListener() {
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

                // like
                binding.btnReviewLike.isSelected = responseValue.data.like.isLiked
                binding.executePendingBindings()
            }
        }
    }


    companion object {
        const val TAG = "ReviewDetailActivity"
        const val NOT_POST_ID = -1
    }

}