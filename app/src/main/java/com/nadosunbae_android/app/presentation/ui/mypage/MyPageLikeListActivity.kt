package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPageLikeListBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeQuestionAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPageLikeQuestionData
import com.nadosunbae_android.domain.model.mypage.MyPagePostData
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageLikeListActivity : BaseActivity<ActivityMyPageLikeListBinding>(R.layout.activity_my_page_like_list) {
    private val myPageViewModel : MyPageViewModel by viewModel()
    private lateinit var myPageLikeQuestionAdapter: MyPageLikeQuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backBtn()
        initBtn()
        selectOption()
    }

    private fun backBtn() {
        binding.imgMypageLikeTitle.setOnClickListener {
            finish()
        }
    }

    private fun initBtn() {

        binding.textMypageLikeReview.isSelected = true
        binding.textMypageLikeQuestion.isSelected = false
        binding.textMypageLikeInfo.isSelected = false
    }

    private fun selectOption() {
        binding.textMypageLikeReview.setOnClickListener {
            binding.textMypageLikeReview.isSelected = true
            binding.textMypageLikeQuestion.isSelected = false
            binding.textMypageLikeInfo.isSelected = false
        }

        binding.textMypageLikeQuestion.setOnClickListener {
            questionPosting()
            binding.textMypageLikeReview.isSelected = false
            binding.textMypageLikeQuestion.isSelected = true
            binding.textMypageLikeInfo.isSelected = false
        }

        binding.textMypageLikeInfo.setOnClickListener {
            infoPosting()
            binding.textMypageLikeReview.isSelected = false
            binding.textMypageLikeQuestion.isSelected = false
            binding.textMypageLikeInfo.isSelected = true
        }
    }

    private fun questionPosting() {
        intent.getIntExtra("userId", 0)
        Log.d("PostuserId", "- id: " + intent.getIntExtra("userId", 0))

        myPageViewModel.getMyPageLikeQuestion("question")
        myPageLikeQuestionAdapter = MyPageLikeQuestionAdapter(2,  intent.getIntExtra("userId", 0),1)
        binding.rvMypageLike.adapter = myPageLikeQuestionAdapter

        myPageViewModel.likeQuestion.observe(this) {
            myPageLikeQuestionAdapter.setQuestionPost((it.data.likePostList) as MutableList<MyPageLikeQuestionData.Data.LikePost>)
        }
    }

    private fun infoPosting() {
        intent.getIntExtra("userId", 0)
        Log.d("PostuserId", "- id: " + intent.getIntExtra("userId", 0))

        myPageViewModel.getMyPageLikeQuestion("information")
        myPageLikeQuestionAdapter = MyPageLikeQuestionAdapter(2,  intent.getIntExtra("userId", 0),1)
        binding.rvMypageLike.adapter = myPageLikeQuestionAdapter

        myPageViewModel.likeQuestion.observe(this) {
            myPageLikeQuestionAdapter.setQuestionPost((it.data.likePostList) as MutableList<MyPageLikeQuestionData.Data.LikePost>)
        }
    }
}