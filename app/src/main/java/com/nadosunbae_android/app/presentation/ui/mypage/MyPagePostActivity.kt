package com.nadosunbae_android.app.presentation.ui.mypage


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPagePostBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.custom.CustomSwitchTab
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostInfoAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPagePostActivity : BaseActivity<ActivityMyPagePostBinding>(R.layout.activity_my_page_post) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    private lateinit var myPagePostInfoAdapter: MyPagePostInfoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionPosting()
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

    //질문 엠티뷰
    private fun initQuestionEmpty(size: Int) {
        if (size == 0) {
            binding.textQuestionEmpty.visibility = View.VISIBLE
            binding.textInfoEmpty.visibility = View.GONE
        } else {
            binding.textQuestionEmpty.visibility = View.GONE
            binding.textInfoEmpty.visibility = View.GONE
        }
    }

    //정보 엠티뷰
    private fun initInfoEmpty(size: Int) {
        if (size == 0) {
            binding.textInfoEmpty.visibility = View.VISIBLE
            binding.textQuestionEmpty.visibility = View.GONE
        } else {
            binding.textInfoEmpty.visibility = View.GONE
            binding.textQuestionEmpty.visibility = View.GONE
        }
    }


    private fun backBtn() {
        binding.imgMypagePostTitle.setOnClickListener {
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
                getString(com.nadosunbae_android.app.R.string.question_detail_title), getString(
                    com.nadosunbae_android.app.R.string.navigation_community
                )
            )
            itemClickListener = {
                if (it != myPageViewModel.postCurFragment.value && !(it == 0 && myPageViewModel.postCurFragment.value == -1)) {
                    switchTab =
                        com.nadosunbae_android.app.presentation.ui.community.custom.CustomSwitchTab.getSwitchTabValue(
                            it
                        )
                    myPageViewModel.postCurFragment.postValue(it)
                }
            }
        }
    }

    private fun observeFragmentNum() {
        myPageViewModel.postCurFragment.observe(this) {
            when (it) {
                0 -> {
                    //1:1질문 서버통신
                    questionPosting()
                }
                1 -> {
                    //커뮤니티 서버통신
                    infoPosting()
                }
            }
            binding.viewMypageSwitch.switchTab = CustomSwitchTab.getSwitchTabValue(it)
        }
    }


    private fun questionPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPost("questionToPerson")
        myPagePostInfoAdapter = MyPagePostInfoAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPagePostInfoAdapter

        myPageViewModel.userPost.observe(this) {
            initQuestionEmpty(it.size)
            (binding.rvMypageQuestion.adapter as MyPagePostInfoAdapter).submitList(it)
        }
    }


    override fun onRestart() {
        super.onRestart()
        /*
        if (binding.textMypagePostQuestionTitle.isSelected) {
            questionPosting()
        } else {
            infoPosting()
        }

         */
    }


    private fun infoPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPost("community")
        myPagePostInfoAdapter = MyPagePostInfoAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPagePostInfoAdapter

        myPageViewModel.userPost.observe(this) {
            initInfoEmpty(it.size)
            (binding.rvMypageQuestion.adapter as MyPagePostInfoAdapter).submitList(it)
        }
    }

}