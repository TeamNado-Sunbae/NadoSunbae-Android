package com.nadosunbae_andorid.presentation.ui.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.data.model.response.review.ReviewTagBoxData
import com.nadosunbae_andorid.databinding.ActivityReviewDetailBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity
import com.nadosunbae_andorid.presentation.ui.review.adapter.ReviewTagBoxAdapter

class ReviewDetailActivity : BaseActivity<ActivityReviewDetailBinding>(R.layout.activity_review_detail) {

    private lateinit var reviewTagBoxAdapter: ReviewTagBoxAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_detail)

        initTagBoxAdapter()
        observeBackground()
        setTestData()
    }

    private fun initTagBoxAdapter() {
        reviewTagBoxAdapter = ReviewTagBoxAdapter()
        binding.rvReviewDetail.adapter = reviewTagBoxAdapter
    }

    private fun observeBackground() {

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
    }


    /*
    배경 샘플 이미지
    https://user-images.githubusercontent.com/37872134/149095580-e2987cf0-39ff-4663-ab91-4899620d8203.png
     */

}