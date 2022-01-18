package com.nadosunbae_android.presentation.ui.review

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.nadosunbae_android.databinding.ActivityReviewWriteBinding

class ReviewRequireTextWatcher(private var binding: ActivityReviewWriteBinding) : TextWatcher {

    private var optionalEditTexts = mutableListOf<EditText>()

    init {
        optionalEditTexts.addAll(
            mutableListOf(
                binding.etCurriculum.editText,
                binding.etRecommendLecture.editText,
                binding.etNonRecommendLecture.editText,
                binding.etCareer.editText,
                binding.etTip.editText
            )
        )
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

        // 한줄평 40자 이상
        val validOneLine = binding.etOneLine.text.length >= MIN_LENGTH_ONELINE
        // 장단점 100자 이상
        val validProsCons = binding.etProsCons.editText.text.length >= MIN_LENGTH_GENERAL
        // 선택작성 100자 이상 최소 1개
        var validOptional = false

        for (e in optionalEditTexts) {
            if (e.text.length >= MIN_LENGTH_GENERAL)
                validOptional = true
            else if (e.text.isNotEmpty()) {
                validOptional = false
                break
            }
        }

        // 조건 만족
        binding.btnWriteComplete.isEnabled = validOneLine && validProsCons && validOptional
    }

    companion object {
        const val MIN_LENGTH_ONELINE = 1
        const val MIN_LENGTH_GENERAL = 100
    }
}