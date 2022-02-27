package com.nadosunbae_android.app.presentation.ui.mypage

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
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

class MyPageReplyActivity :
    BaseActivity<ActivityMyPageReplyBinding>(R.layout.activity_my_page_reply) {

    private val myPageViewModel: MyPageViewModel by viewModel()

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
        binding.apply {
            questionPosting()
            textMypageReplyQuestionTitle.isSelected = true
            textMypageReplyInfoTitle.isSelected = false

            //폰트 설정
            textMypageReplyQuestionTitle.typeface =
                ResourcesCompat.getFont(Activity(), R.font.pretendard_semibold)
            textMypageReplyInfoTitle.typeface =
                ResourcesCompat.getFont(Activity(), R.font.pretendard_regular)
        }

    }

    private fun selectOption() {
        binding.apply {
            textMypageReplyQuestionTitle.setOnClickListener {
                questionPosting()
                textMypageReplyQuestionTitle.isSelected = true
                textMypageReplyInfoTitle.isSelected = false

                //폰트 설정
                textMypageReplyQuestionTitle.typeface =
                    ResourcesCompat.getFont(Activity(), R.font.pretendard_semibold)
                textMypageReplyInfoTitle.typeface =
                    ResourcesCompat.getFont(Activity(), R.font.pretendard_regular)
            }

            textMypageReplyInfoTitle.setOnClickListener {
                infoPosting()
                textMypageReplyQuestionTitle.isSelected = false
                textMypageReplyInfoTitle.isSelected = true

                //폰트 설정
                textMypageReplyQuestionTitle.typeface =
                    ResourcesCompat.getFont(Activity(), R.font.pretendard_regular)
                textMypageReplyInfoTitle.typeface =
                    ResourcesCompat.getFont(Activity(), R.font.pretendard_semibold)
            }

        }
    }

    private fun questionPosting() {
        intent.getIntExtra("userId", 0)
        Log.d("ReplyuserId", "- id: " + intent.getIntExtra("userId", 0))

        myPageViewModel.getMyPageReply(3)
        myPageReplyAdapter = MyPageReplyAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPageReplyAdapter

        myPageViewModel.replyByMe.observe(this) {
            myPageReplyAdapter.setQuestionReply((it.data.classroomPostListByMyCommentList) as MutableList<MyPageReplyData.Data.ClassroomPostListByMyComment>)
        }
    }

    private fun infoPosting() {
        intent.getIntExtra("userId", 0)
        Log.d("ReplyuserId", "- id: " + intent.getIntExtra("userId", 0))

        myPageViewModel.getMyPageReply(2)
        myPageReplyAdapter = MyPageReplyAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPageReplyAdapter

        myPageViewModel.replyByMe.observe(this) {
            myPageReplyAdapter.setQuestionReply((it.data.classroomPostListByMyCommentList) as MutableList<MyPageReplyData.Data.ClassroomPostListByMyComment>)
        }
    }
}