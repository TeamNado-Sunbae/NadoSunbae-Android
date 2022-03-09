package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPageClassroomReviewBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeReviewAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageReviewAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.review.ReviewDetailActivity
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.domain.model.mypage.MyPageLikeReviewData
import com.nadosunbae_android.domain.model.mypage.MyPageReviewData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyPageClassroomReviewActivity : BaseActivity<ActivityMyPageClassroomReviewBinding>(R.layout.activity_my_page_classroom_review) {

    private val myPageViewModel: MyPageViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var myPageReviewAdapter: MyPageReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLoadingEnd()
        initReviewListAdapter()
        backBtn()
        initNickName()

    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }

    //정보 엠티뷰
    private fun initReviewEmpty(size : Int){
        if(size == 0){
            binding.textReviewEmpty.visibility = View.VISIBLE
        }else{
            binding.textReviewEmpty.visibility = View.GONE
        }
    }

    private fun initNickName(){
        binding.textMypageReviewNickname.setText(intent.getStringExtra("userNickName"))
    }

    private fun backBtn() {
        binding.imgMypageReviewTitle.setOnClickListener {
            finish()
        }
    }

    override fun onRestart() {
        super.onRestart()
        initReviewListAdapter()
    }

    private fun initReviewListAdapter() {
        showLoading()
        myPageViewModel.getMyPageReview(intent.getIntExtra("userId", 0))
        myPageReviewAdapter = MyPageReviewAdapter()
        binding.rvMypageReview.adapter = myPageReviewAdapter
        myPageViewModel.reviewList.observe(this) {
            initReviewEmpty(it.data.reviewPostList.size)
            myPageReviewAdapter.setReviewListData((it.data.reviewPostList) as MutableList<MyPageReviewData.Data.ReviewPost>)
        }
    }
}