package com.nadosunbae_android.presentation.ui.review

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.review.ReviewTagBoxData
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
        setClickListener()
        observeBackground()
        setTestData()

    }

    private fun initTagBoxAdapter() {
        reviewTagBoxAdapter = ReviewTagBoxAdapter()
        binding.rvReviewDetail.adapter = reviewTagBoxAdapter
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.reviewDetailViewModel = reviewDetailViewModel
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun observeBackground() {

        reviewDetailViewModel.urlBackground.observe(this) {

        }
    }

    private fun setTestData() {

        val sampleData = ReviewTagBoxData(
            "label name ajajajaj",
            "content content dkfjsdiofxkzlfs jfdsj aodifj sidf jsdlk fewoi flkdsj sdjf ioewjfowjf egoiew jfioj "
        )
        val sampleList = mutableListOf<ReviewTagBoxData>(
            sampleData,
            sampleData,
            sampleData
        )
        reviewTagBoxAdapter.setReviewTagBoxData(sampleList)

        reviewDetailViewModel.setBackgroundUrl("https://user-images.githubusercontent.com/37872134/149095580-e2987cf0-39ff-4663-ab91-4899620d8203.png")
    }

}