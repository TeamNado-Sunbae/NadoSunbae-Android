package com.nadosunbae_android.app.presentation.ui.mypage


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPagePostBinding
import com.nadosunbae_android.app.di.NadoSunBaeApplication.Companion.context
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageMainAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostInfoAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPagePostData
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
@AndroidEntryPoint
class MyPagePostActivity : BaseActivity<ActivityMyPagePostBinding>(R.layout.activity_my_page_post) {

    private val myPageViewModel: MyPageViewModel by viewModel()

    private lateinit var myPagePostAdapter: MyPagePostAdapter
    private lateinit var myPagePostInfoAdapter: MyPagePostInfoAdapter


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

    //질문 엠티뷰
    private fun initQuestionEmpty(size : Int){
        if(size == 0){
            binding.textQuestionEmpty.visibility = View.VISIBLE
            binding.textInfoEmpty.visibility = View.GONE
        }else{
            binding.textQuestionEmpty.visibility = View.GONE
            binding.textInfoEmpty.visibility = View.GONE
        }
    }

    //정보 엠티뷰
    private fun initInfoEmpty(size : Int){
        if(size == 0){
            binding.textInfoEmpty.visibility = View.VISIBLE
            binding.textQuestionEmpty.visibility = View.GONE
        }else{
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


    private fun initBtn() {
        binding.apply {
            showLoading()
            questionPosting()
            textMypagePostQuestionTitle.isSelected = true
            textMypagePostInfoTitle.isSelected = false

            //폰트 설정
            textMypagePostQuestionTitle.typeface =
                ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
            textMypagePostInfoTitle.typeface =
                ResourcesCompat.getFont(context(), R.font.pretendard_regular)
        }

    }

    private fun selectOption() {
        binding.apply {
            textMypagePostQuestionTitle.setOnClickListener {
                showLoading()
                questionPosting()
                textMypagePostQuestionTitle.isSelected = true
                textMypagePostInfoTitle.isSelected = false

                //폰트 설정
                textMypagePostQuestionTitle.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
                textMypagePostInfoTitle.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
            }

            textMypagePostInfoTitle.setOnClickListener {
                showLoading()
                infoPosting()
                textMypagePostQuestionTitle.isSelected = false
                textMypagePostInfoTitle.isSelected = true

                //폰트 설정
                textMypagePostQuestionTitle.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
                textMypagePostInfoTitle.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
            }
        }

    }


    private fun questionPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPagePost("question")
        myPagePostAdapter = MyPagePostAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPagePostAdapter

        myPageViewModel.postByMe.observe(this) {
            initQuestionEmpty(it.data.classroomPostList.size)
            myPagePostAdapter.setQuestionPost((it.data.classroomPostList) as MutableList<MyPagePostData.Data.ClassroomPost>)
        }
    }


    override fun onRestart() {
        super.onRestart()
        if(binding.textMypagePostQuestionTitle.isSelected) {
            questionPosting()
        } else {
            infoPosting()
        }
    }


    private fun infoPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPagePost("information")
        myPagePostInfoAdapter = MyPagePostInfoAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageQuestion.adapter = myPagePostInfoAdapter

        myPageViewModel.postByMe.observe(this) {
            initInfoEmpty(it.data.classroomPostList.size)
            myPagePostInfoAdapter.setQuestionPost((it.data.classroomPostList) as MutableList<MyPagePostData.Data.ClassroomPost>)
        }
    }

}