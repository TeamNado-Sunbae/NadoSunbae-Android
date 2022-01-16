package com.nadosunbae_andorid.presentation.ui.review

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivityReviewWriteBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity
import com.nadosunbae_andorid.presentation.ui.review.adapter.ReviewSelectBackgroundAdapter

class ReviewWriteActivity : BaseActivity<ActivityReviewWriteBinding>(R.layout.activity_review_write) {

    private lateinit var reviewSelectBackgroundAdapter: ReviewSelectBackgroundAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initReviewSelectBackgroundAdapter()
        setOneLineTextWatcher()

        setTestData()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
    }

    private fun initReviewSelectBackgroundAdapter() {
        reviewSelectBackgroundAdapter = ReviewSelectBackgroundAdapter()
        binding.rvSelectBackground.adapter = reviewSelectBackgroundAdapter
    }

    private fun setOneLineTextWatcher() {
        binding.etOneLine.addTextChangedListener(object : TextWatcher {

            private var prevString = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                prevString = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // 최대 글자수 체크 및 개행 문자 방지 (한줄평이므로)
                if (s?.length!! > ONE_LINE_MAX_LENGTH || binding.etOneLine.text.contains("\n")) {
                    binding.etOneLine.setText(prevString)
                    binding.etOneLine.setSelection(prevString.length - 1)
                }

            }

        })

    }

    private fun setTestData() {
        reviewSelectBackgroundAdapter.dataList.addAll(
            mutableListOf("https://cdn.zeplin.io/61d5107362df6f18539e470d/assets/a87872d0-0419-47de-a5c2-8a278ac4828a-4x.png",
                "https://cdn.zeplin.io/61d5107362df6f18539e470d/assets/82cf7d22-f4f7-41dd-b230-3474e1377bf9-4x.png",
                "https://cdn.zeplin.io/61d5107362df6f18539e470d/assets/a87872d0-0419-47de-a5c2-8a278ac4828a-4x.png",
                "https://cdn.zeplin.io/61d5107362df6f18539e470d/assets/a87872d0-0419-47de-a5c2-8a278ac4828a-4x.png",
                "https://cdn.zeplin.io/61d5107362df6f18539e470d/assets/a87872d0-0419-47de-a5c2-8a278ac4828a-4x.png",
                "https://cdn.zeplin.io/61d5107362df6f18539e470d/assets/a87872d0-0419-47de-a5c2-8a278ac4828a-4x.png",
                "https://cdn.zeplin.io/61d5107362df6f18539e470d/assets/a87872d0-0419-47de-a5c2-8a278ac4828a-4x.png"
        
            )
        )
        reviewSelectBackgroundAdapter.notifyDataSetChanged()
    }

    companion object {
        const val ONE_LINE_MAX_LENGTH = 40
    }

}