package com.nadosunbae_android.presentation.ui.review

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.ActivityReviewDetailBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewTagBoxAdapter
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewDetailViewModel


class ReviewDetailActivity :
    BaseActivity<ActivityReviewDetailBinding>(R.layout.activity_review_detail) {

    private lateinit var reviewTagBoxAdapter: ReviewTagBoxAdapter

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
        val postId = intent.getIntExtra("postId", NOT_POST_ID)

        // intent extra check
        if (postId != NOT_POST_ID)
            reviewDetailViewModel.getReviewDetail(postId)

    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnReviewLike.setOnClickListener {
            binding.btnReviewLike.isSelected = !binding.btnReviewLike.isSelected
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
                var backgroundRes = R.drawable.img_bg_1
                when (responseValue.data.backgroundImage.imageId) {
                    1 -> backgroundRes = R.drawable.img_bg_1
                    2 -> backgroundRes = R.drawable.img_bg_2
                    3 -> backgroundRes = R.drawable.img_bg_3
                    4 -> backgroundRes = R.drawable.img_bg_4
                    5 -> backgroundRes = R.drawable.img_bg_5
                    6 -> backgroundRes = R.drawable.img_bg_6
                    7 -> backgroundRes = R.drawable.img_bg_7
                }
                reviewDetailViewModel.setBackgroundRes(resources.getDrawable(backgroundRes))
            }
        }
    }


    companion object {
        const val TAG = "ReviewDetailActivity"
        const val NOT_POST_ID = -1
    }

}