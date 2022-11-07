package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPageLikeListBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.custom.CustomSwitchTab
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeQuestionAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeReviewAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageReviewAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MyPageLikeListActivity :
    BaseActivity<ActivityMyPageLikeListBinding>(R.layout.activity_my_page_like_list) {
    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var myPageLikeQuestionAdapter: MyPageLikeQuestionAdapter
    private lateinit var myPageLikeReviewAdapter: MyPageLikeReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoadingEnd()
        backBtn()
        initSwitchTab()
        observeFragmentNum()
        initReviewListAdapter()
    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }

    private fun backBtn() {
        binding.imgMypageLikeTitle.setOnClickListener {
            showLoading()
            finish()
        }
    }

    private fun initSwitchTab() {

        with(binding.viewMypageSwitch) {
            switchTab =
                CustomSwitchTab.getSwitchTabValue(
                    0
                )
            switchText = listOf(
                getString(com.nadosunbae_android.app.R.string.review_reviews), getString(
                    com.nadosunbae_android.app.R.string.question_detail_title
                ), getString(
                    com.nadosunbae_android.app.R.string.navigation_community
                )
            )
            itemClickListener = {
                if (it != myPageViewModel.likeCurFragment.value && !(it == 0 && myPageViewModel.likeCurFragment.value == -1)) {
                    switchTab =
                        CustomSwitchTab.getSwitchTabValue(
                            it
                        )
                    myPageViewModel.likeCurFragment.postValue(it)
                }
            }
        }
    }

    private fun observeFragmentNum() {
        myPageViewModel.likeCurFragment.observe(this) {
            when (it) {
                0 -> {
                    initReviewListAdapter()
                }
                1 -> {
                    questionPosting()
                }
                2 -> {
                    infoPosting()
                }
            }
            binding.viewMypageSwitch.switchTab = CustomSwitchTab.getSwitchTabValue(it)
        }
    }


    //후기 엠티뷰
    private fun initReviewEmpty(size: Int) {
        if (size == 0) {
            binding.textReviewEmpty.visibility = View.VISIBLE
            binding.textInfoEmpty.visibility = View.GONE
            binding.textQuestionEmpty.visibility = View.GONE
        } else {
            binding.textReviewEmpty.visibility = View.GONE
            binding.textQuestionEmpty.visibility = View.GONE
            binding.textInfoEmpty.visibility = View.GONE
        }
    }

    //정보 엠티뷰
    private fun initInfoEmpty(size: Int) {
        if (size == 0) {
            binding.textReviewEmpty.visibility = View.GONE
            binding.textInfoEmpty.visibility = View.VISIBLE
            binding.textQuestionEmpty.visibility = View.GONE
        } else {
            binding.textReviewEmpty.visibility = View.GONE
            binding.textQuestionEmpty.visibility = View.GONE
            binding.textInfoEmpty.visibility = View.GONE
        }
    }

    // 질문 엠티뷰
    private fun initQuestionEmpty(size: Int) {
        if (size == 0) {
            binding.textReviewEmpty.visibility = View.GONE
            binding.textInfoEmpty.visibility = View.GONE
            binding.textQuestionEmpty.visibility = View.VISIBLE
        } else {
            binding.textReviewEmpty.visibility = View.GONE
            binding.textQuestionEmpty.visibility = View.GONE
            binding.textInfoEmpty.visibility = View.GONE
        }
    }

    //1:1 질문
    private fun questionPosting() {
        myPageViewModel.getMyPageLike("questionToPerson")
        myPageLikeQuestionAdapter =
            MyPageLikeQuestionAdapter(2, intent.getIntExtra("userId", 0), 1, 0)
        binding.rvMypageLike.adapter = myPageLikeQuestionAdapter
        binding.rvMypageLike.visibility = View.VISIBLE
        binding.rvMypageReviewLike.visibility = View.GONE
        myPageViewModel.userLike.observe(this) {
            initQuestionEmpty(it.size)
            (binding.rvMypageLike.adapter as MyPageLikeQuestionAdapter).submitList(it)
        }
    }

    //커뮤니티
    private fun infoPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)

        myPageViewModel.getMyPageLike("community")
        myPageLikeQuestionAdapter = MyPageLikeQuestionAdapter(2, intent.getIntExtra("userId", 0), 1, 1)
        binding.rvMypageLike.adapter = myPageLikeQuestionAdapter
        binding.rvMypageLike.visibility = View.VISIBLE
        binding.rvMypageReviewLike.visibility = View.GONE
        myPageViewModel.userLike.observe(this) {
            initInfoEmpty(it.size)
            (binding.rvMypageLike.adapter as MyPageLikeQuestionAdapter).submitList(it)
        }
    }


    //후기
    private fun initReviewListAdapter() {
        showLoading()
        val userId = intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPageLike("review")
        myPageLikeReviewAdapter = MyPageLikeReviewAdapter(userId)
        binding.rvMypageReviewLike.adapter = myPageLikeReviewAdapter
        binding.rvMypageLike.visibility = View.GONE
        binding.rvMypageReviewLike.visibility = View.VISIBLE
        myPageViewModel.userLike.observe(this)
        {
            initReviewEmpty(it.size)
            (binding.rvMypageReviewLike.adapter as MyPageLikeReviewAdapter).submitList(it)
        }
    }
}
