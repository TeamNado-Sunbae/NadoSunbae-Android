package com.nadosunbae_andorid.presentation.ui.review

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivityReviewWriteBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity


class ReviewWriteActivity : BaseActivity<ActivityReviewWriteBinding>(R.layout.activity_review_write) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        setOneLineTextWatcher()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
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

    companion object {
        const val ONE_LINE_MAX_LENGTH = 40
    }

}