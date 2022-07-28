package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPageReplyBinding
import com.nadosunbae_android.app.di.NadoSunBaeApplication.Companion.context
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageReplyAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageReplyInfoAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPageReplyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageReplyActivity :
    BaseActivity<ActivityMyPageReplyBinding>(R.layout.activity_my_page_reply) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    private lateinit var myPageReplyAdapter: MyPageReplyAdapter
    private lateinit var myPageeReplyInfoAdapter: MyPageReplyInfoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoadingEnd()
        initBtn()
        backBtn()
        selectOption()
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


    private fun initBtn() {
        binding.apply {
            showLoading()
            questionPosting()
            textMypageReplyQuestionTitle.isSelected = true
            textMypageReplyInfoTitle.isSelected = false

            //폰트 설정
            textMypageReplyQuestionTitle.typeface =
                ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
            textMypageReplyInfoTitle.typeface =
                ResourcesCompat.getFont(context(), R.font.pretendard_regular)
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

    private fun selectOption() {
        binding.apply {
            textMypageReplyQuestionTitle.setOnClickListener {
                showLoading()
                questionPosting()
                textMypageReplyQuestionTitle.isSelected = true
                textMypageReplyInfoTitle.isSelected = false

                //폰트 설정
                textMypageReplyQuestionTitle.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
                textMypageReplyInfoTitle.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
            }

            textMypageReplyInfoTitle.setOnClickListener {
                showLoading()
                infoPosting()
                textMypageReplyQuestionTitle.isSelected = false
                textMypageReplyInfoTitle.isSelected = true

                //폰트 설정
                textMypageReplyQuestionTitle.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
                textMypageReplyInfoTitle.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
            }

        }
    }

    private fun questionPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPageReply(3)
        myPageReplyAdapter = MyPageReplyAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPageReplyAdapter

        myPageViewModel.replyByMe.observe(this) {
            initQuestionEmpty(it.data.classroomPostListByMyCommentList.size)
            myPageReplyAdapter.setQuestionReply((it.data.classroomPostListByMyCommentList) as MutableList<MyPageReplyData.Data.ClassroomPostListByMyComment>)
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (binding.textMypageReplyQuestionTitle.isSelected) {
            questionPosting()
        } else {
            infoPosting()
        }
    }

    private fun infoPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPageReply(2)
        myPageeReplyInfoAdapter = MyPageReplyInfoAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPageeReplyInfoAdapter

        myPageViewModel.replyByMe.observe(this) {
            initInfoEmpty(it.data.classroomPostListByMyCommentList.size)
            myPageeReplyInfoAdapter.setQuestionReply((it.data.classroomPostListByMyCommentList) as MutableList<MyPageReplyData.Data.ClassroomPostListByMyComment>)
        }
    }

}