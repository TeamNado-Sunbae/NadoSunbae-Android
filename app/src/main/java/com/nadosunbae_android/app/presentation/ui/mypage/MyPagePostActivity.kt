package com.nadosunbae_android.app.presentation.ui.mypage


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPagePostBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageMainAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPagePostData
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import org.koin.android.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPagePostActivity : BaseActivity<ActivityMyPagePostBinding>(R.layout.activity_my_page_post) {

    private val myPageViewModel : MyPageViewModel by viewModel()

    private lateinit var myPagePostAdapter: MyPagePostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBtn()
        backBtn()
        selectOption()
    }


    private fun backBtn() {
        binding.imgMypagePostTitle.setOnClickListener {
            finish()
        }
    }


    private fun initBtn() {
        questionPosting()
        binding.textMypagePostQuestionTitle.isSelected = true
        binding.textMypagePostInfoTitle.isSelected = false
    }

    private fun selectOption() {
        binding.textMypagePostQuestionTitle.setOnClickListener {
            questionPosting()
            binding.textMypagePostQuestionTitle.isSelected = true
            binding.textMypagePostInfoTitle.isSelected = false
        }
        binding.textMypagePostInfoTitle.setOnClickListener {
            infoPosting()
            binding.textMypagePostQuestionTitle.isSelected = false
            binding.textMypagePostInfoTitle.isSelected = true
        }
    }


    private fun questionPosting() {
        intent.getIntExtra("userId", 0)
        Log.d("PostuserId", "- id: " + intent.getIntExtra("userId", 0))

        myPageViewModel.getMyPagePost("question")
        myPagePostAdapter = MyPagePostAdapter(2,  intent.getIntExtra("userId", 0),1)
        binding.rvMypageQuestion.adapter = myPagePostAdapter

        myPageViewModel.postByMe.observe(this) {
            myPagePostAdapter.setQuestionPost((it.data.classroomPostList) as MutableList<MyPagePostData.Data.ClassroomPost>)
        }
    }

    private fun infoPosting() {
        intent.getIntExtra("userId", 0)
        Log.d("PostuserId", "- id: " + intent.getIntExtra("userId", 0))

        myPageViewModel.getMyPagePost("information")
        myPagePostAdapter = MyPagePostAdapter(2,  intent.getIntExtra("userId", 0),1)
        binding.rvMypageQuestion.adapter = myPagePostAdapter

        myPageViewModel.postByMe.observe(this) {
            myPagePostAdapter.setQuestionPost((it.data.classroomPostList) as MutableList<MyPagePostData.Data.ClassroomPost>)
        }
    }

}