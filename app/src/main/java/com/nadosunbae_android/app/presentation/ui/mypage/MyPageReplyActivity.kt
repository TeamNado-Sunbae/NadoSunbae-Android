package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPageReplyBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.custom.CustomSwitchTab
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostInfoAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageReplyActivity :
    BaseActivity<ActivityMyPageReplyBinding>(R.layout.activity_my_page_reply) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    private lateinit var myPagePostInfoAdapter: MyPagePostInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoadingEnd()
        backBtn()
        initSwitchTab()
        observeFragmentNum()
        questionPosting()
    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }

    private fun backBtn() {
        binding.imgMypageReplyTitle.setOnClickListener {
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
                getString(com.nadosunbae_android.app.R.string.question_detail_title), getString(
                    com.nadosunbae_android.app.R.string.navigation_community
                )
            )
            itemClickListener = {
                if (it != myPageViewModel.applyCurFragment.value && !(it == 0 && myPageViewModel.applyCurFragment.value == -1)) {
                    switchTab =
                        CustomSwitchTab.getSwitchTabValue(
                            it
                        )
                    myPageViewModel.applyCurFragment.postValue(it)
                }
            }
        }
    }

    private fun observeFragmentNum() {
        myPageViewModel.applyCurFragment.observe(this) {
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


    private fun questionPosting() {
        myPageViewModel.getMyPageReply("questionToPerson")
        myPagePostInfoAdapter = MyPagePostInfoAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPagePostInfoAdapter

        myPageViewModel.userComment.observe(this) {
            initQuestionEmpty(it.size)
            (binding.rvMypageQuestion.adapter as MyPagePostInfoAdapter).submitList(it)
        }
    }

    override fun onRestart() {
        /*
        super.onRestart()
        if (binding.textMypageReplyQuestionTitle.isSelected) {
            questionPosting()
        } else {
            infoPosting()
        }
         */
    }

    private fun infoPosting() {
        myPageViewModel.getMyPageReply("community")
        myPagePostInfoAdapter = MyPagePostInfoAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPagePostInfoAdapter

        myPageViewModel.userComment.observe(this) {
            initInfoEmpty(it.size)
            (binding.rvMypageQuestion.adapter as MyPagePostInfoAdapter).submitList(it)
        }
    }

}