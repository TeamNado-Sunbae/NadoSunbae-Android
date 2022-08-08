package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPageLikeListBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.custom.CustomSwitchTab
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeQuestionAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeQuestionInfoAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeReviewAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPageLikeQuestionData
import com.nadosunbae_android.domain.model.mypage.MyPageLikeReviewData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageLikeListActivity :
    BaseActivity<ActivityMyPageLikeListBinding>(R.layout.activity_my_page_like_list) {
    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var myPageLikeQuestionAdapter: MyPageLikeQuestionAdapter
    private lateinit var myPageLikeReviewAdapter: MyPageLikeReviewAdapter
    private lateinit var myPageLikeQuestionInfoAdapter: MyPageLikeQuestionInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoadingEnd()
        backBtn()
        initSwitchTab()
        observeFragmentNum()
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
                com.nadosunbae_android.app.presentation.ui.community.custom.CustomSwitchTab.getSwitchTabValue(
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
                        com.nadosunbae_android.app.presentation.ui.community.custom.CustomSwitchTab.getSwitchTabValue(
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
                    //1:1질문 서버통신
                    Timber.d("1:1")
                }
                1 -> {
                    //커뮤니티 서버통신
                    Timber.d("커뮤니티")
                }
                2 -> {

                }
            }
            binding.viewMypageSwitch.switchTab = CustomSwitchTab.getSwitchTabValue(it)
        }
    }


    override fun onRestart() {
        /*
        super.onRestart()
        if (binding.textMypageLikeReview.isSelected) {
            initReviewListAdapter()
        } else if (binding.textMypageLikeQuestion.isSelected) {
            questionPosting()
        } else {
            infoPosting()
        }

         */
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

    private fun questionPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPageLikeQuestion("question")
        myPageLikeQuestionAdapter =
            MyPageLikeQuestionAdapter(2, intent.getIntExtra("userId", 0), 1, 0)
        binding.rvMypageLike.adapter = myPageLikeQuestionAdapter

        myPageViewModel.likeQuestion.observe(this) {
            initQuestionEmpty(it.data.likePostList.size)
            myPageLikeQuestionAdapter.setQuestionPost((it.data.likePostList) as MutableList<MyPageLikeQuestionData.Data.LikePost>)
        }
    }

    private fun infoPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)

        myPageViewModel.getMyPageLikeQuestion("information")
        myPageLikeQuestionInfoAdapter =
            MyPageLikeQuestionInfoAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageLike.adapter = myPageLikeQuestionInfoAdapter

        myPageViewModel.likeQuestion.observe(this) {
            initInfoEmpty(it.data.likePostList.size)
            myPageLikeQuestionInfoAdapter.setQuestionPost((it.data.likePostList) as MutableList<MyPageLikeQuestionData.Data.LikePost>)
        }
    }


    private fun initReviewListAdapter() {
        showLoading()
        val userId = intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPageLikeReview("review")
        myPageLikeReviewAdapter = MyPageLikeReviewAdapter(userId)
        binding.rvMypageLike.adapter = myPageLikeReviewAdapter

        myPageViewModel.likeReview.observe(this) {
            initReviewEmpty(it.data.likePostList.size)
            myPageLikeReviewAdapter.setReviewListData((it.data.likePostList) as MutableList<MyPageLikeReviewData.Data.LikePost>)
        }
    }
}
