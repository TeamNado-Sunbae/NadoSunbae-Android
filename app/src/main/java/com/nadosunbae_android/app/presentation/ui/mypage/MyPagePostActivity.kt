package com.nadosunbae_android.app.presentation.ui.mypage


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPagePostBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageMainAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPagePostData
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import org.koin.android.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPagePostActivity : BaseActivity<ActivityMyPagePostBinding>(R.layout.activity_my_page_post) {

    private val myPageViewModel : MyPageViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var myPagePostAdapter: MyPagePostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionPosting()
    }

    /*
    private fun initBtn() {
        /*
        binding.rvMypageQuestion.visibility = View.VISIBLE
        binding.rvMypageInformaiton.visibility = View.INVISIBLE

         */
        binding.textMypagePostQuestionTitle.isSelected = true
        binding.textMypagePostInfoTitle.isSelected = false
    }

    private fun selectTitle() {
        binding.textMypagePostQuestionTitle.setOnClickListener {
            /*
            binding.rvMypageQuestion.visibility = View.VISIBLE
            binding.rvMypageInformaiton.visibility = View.INVISIBLE

             */
            binding.textMypagePostQuestionTitle.isSelected = true
            binding.textMypagePostInfoTitle.isSelected = false
        }
        binding.textMypagePostInfoTitle.setOnClickListener {
            /*
            binding.rvMypageQuestion.visibility = View.INVISIBLE
            binding.rvMypageInformaiton.visibility = View.VISIBLE

             */
            binding.textMypagePostQuestionTitle.isSelected = false
            binding.textMypagePostInfoTitle.isSelected = true
        }
    }
    */


    private fun questionPosting() {
        mainViewModel.signData.observe(this){
            myPageViewModel.getMyPagePost("question")
        }
        myPagePostAdapter = MyPagePostAdapter(2, mainViewModel.userId.value ?: 0,1)
        binding.rvMypageQuestion.adapter = myPagePostAdapter
        myPageViewModel.postByMe.observe(this) {
            myPagePostAdapter.setQuestionPost((it.data.classroomPostList) as MutableList<MyPagePostData.Data.ClassroomPost>)
        }
    }

}