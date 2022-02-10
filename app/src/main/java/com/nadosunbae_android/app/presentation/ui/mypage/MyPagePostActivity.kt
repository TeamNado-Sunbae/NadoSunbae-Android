package com.nadosunbae_android.app.presentation.ui.mypage


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPagePostBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel

class MyPagePostActivity : BaseActivity<ActivityMyPagePostBinding>(R.layout.activity_my_page_post) {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBtn()
        selectTitle()
    }

    private fun initBtn() {
        binding.rvMypageQuestion.visibility = View.VISIBLE
        binding.rvMypageInformaiton.visibility = View.INVISIBLE
        binding.textMypagePostQuestionTitle.isSelected = true
        binding.textMypagePostInfoTitle.isSelected = false
    }

    private fun selectTitle() {
        binding.textMypagePostQuestionTitle.setOnClickListener {
            binding.rvMypageQuestion.visibility = View.VISIBLE
            binding.rvMypageInformaiton.visibility = View.INVISIBLE
            binding.textMypagePostQuestionTitle.isSelected = true
            binding.textMypagePostInfoTitle.isSelected = false
        }
        binding.textMypagePostInfoTitle.setOnClickListener {
            binding.rvMypageQuestion.visibility = View.INVISIBLE
            binding.rvMypageInformaiton.visibility = View.VISIBLE
            binding.textMypagePostQuestionTitle.isSelected = false
            binding.textMypagePostInfoTitle.isSelected = true
        }
    }

}