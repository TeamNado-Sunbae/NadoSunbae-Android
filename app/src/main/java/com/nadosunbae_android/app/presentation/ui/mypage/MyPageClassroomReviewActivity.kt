package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPageClassroomReviewBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageReviewAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageClassroomReviewActivity :
    BaseActivity<ActivityMyPageClassroomReviewBinding>(R.layout.activity_my_page_classroom_review) {

    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var myPageReviewAdapter: MyPageReviewAdapter
    var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getReviewListData()
        observeLoadingEnd()
        initReviewListAdapter()
        backBtn()
        initNickName()
        observeMyPagePost()

    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }

    //정보 엠티뷰
    private fun initReviewEmpty(size: Int) {
        if (size == 0) {
            binding.textReviewEmpty.visibility = View.VISIBLE
            binding.rvMypageReview.visibility = View.GONE
        } else {
            binding.textReviewEmpty.visibility = View.GONE
            binding.rvMypageReview.visibility = View.VISIBLE
        }
    }

    private fun initNickName() {
        binding.textMypageReviewNickname.setText(intent.getStringExtra("userNickName"))
    }

    private fun backBtn() {
        binding.imgMypageReviewTitle.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        myPageViewModel.getMyPageReview(myPageViewModel.userId.value ?: 0)
        Timber.d("mypageUserId ${myPageViewModel.userId.value}")
    }

    private fun observeMyPagePost() {
        myPageViewModel.myPagePostStatus.observe(this) {
            if (it == 204) {
                initReviewEmpty(0)
            }
        }
    }

    private fun initReviewListAdapter() {
        Timber.d("mypageUserId ${myPageViewModel.userId.value}")
        myPageViewModel.getMyPageReview(myPageViewModel.userId.value ?: 0)
        myPageReviewAdapter = MyPageReviewAdapter(myPageViewModel.userId.value ?: 0)
        binding.rvMypageReview.adapter = myPageReviewAdapter
        myPageViewModel.userReview.observe(this) {
            initReviewEmpty(it.size)
            (binding.rvMypageReview.adapter as MyPageReviewAdapter).submitList(it)
        }
    }

    //서버 통신
    private fun getReviewListData() {
        val userId = intent.getIntExtra("userId", 0)
        Timber.d("유저 아이디 : $userId")
        Timber.d("현 사용자 아이디 : ${MainGlobals.signInData?.userId}")
        myPageViewModel.userId.value = userId
        showLoading()
        myPageViewModel.getMyPageReview(myPageViewModel.userId.value ?: 0)
    }
}