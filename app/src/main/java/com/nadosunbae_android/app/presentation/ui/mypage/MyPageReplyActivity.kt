package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPagePostBinding
import com.nadosunbae_android.app.databinding.ActivityMyPageReplyBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageReplyAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPagePostData
import com.nadosunbae_android.domain.model.mypage.MyPageReplyData
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageReplyActivity :  BaseActivity<ActivityMyPageReplyBinding>(R.layout.activity_my_page_reply) {

    private val myPageViewModel : MyPageViewModel by viewModel()

    private lateinit var myPageReplyAdapter: MyPageReplyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBtn()
        backBtn()
        selectOption()
    }

    private fun backBtn() {
        binding.imgMypageReplyTitle.setOnClickListener {
            finish()
        }
    }


    private fun initBtn() {
        questionPosting()
        binding.textMypageReplyQuestionTitle.isSelected = true
        binding.textMypageReplyInfoTitle.isSelected = false
    }

    private fun selectOption() {
        binding.textMypageReplyQuestionTitle.setOnClickListener {
            questionPosting()
            binding.textMypageReplyQuestionTitle.isSelected = true
            binding.textMypageReplyInfoTitle.isSelected = false
        }
        binding.textMypageReplyInfoTitle.setOnClickListener {
            infoPosting()
            binding.textMypageReplyQuestionTitle.isSelected = false
            binding.textMypageReplyInfoTitle.isSelected = true
        }
    }

    private fun questionPosting() {
        intent.getIntExtra("userId", 0)
        Log.d("userId", "- id: " + intent.getIntExtra("userId", 0))

        myPageViewModel.getMyPageReply(2)
        myPageReplyAdapter = MyPageReplyAdapter(2,  intent.getIntExtra("userId", 0),1)
        binding.rvMypageQuestion.adapter = myPageReplyAdapter

        myPageViewModel.postByMe.observe(this) {
            myPageReplyAdapter.setQuestionReply((it.data.classroomPostList) as MutableList<MyPageReplyData.Data.ClassroomPostListByMyComment>)
        }
    }

    private fun infoPosting() {
        intent.getIntExtra("userId", 0)
        Log.d("userId", "- id: " + intent.getIntExtra("userId", 0))

        myPageViewModel.getMyPageReply(3)
        myPageReplyAdapter = MyPageReplyAdapter(2,  intent.getIntExtra("userId", 0),1)
        binding.rvMypageQuestion.adapter = myPageReplyAdapter

        myPageViewModel.postByMe.observe(this) {
            myPageReplyAdapter.setQuestionReply((it.data.classroomPostList) as MutableList<MyPageReplyData.Data.ClassroomPostListByMyComment>)
        }
    }
}