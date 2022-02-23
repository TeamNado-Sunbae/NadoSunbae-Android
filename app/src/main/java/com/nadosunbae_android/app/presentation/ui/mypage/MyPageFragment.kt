package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentMyPageBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageMainAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var myPageQuestionAdapter: MyPageMainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAskPersonal()
        movePage()
        initPersonalInfo()
    }



    private fun movePage() {
        binding.textMyPagePostByMe.setOnClickListener {
            val intentMyPagePost = Intent(requireActivity(), MyPagePostActivity::class.java)
            intentMyPagePost.putExtra("userId", mainViewModel.userId.value ?: 0)
            Log.d("mypageFragment userId - ", "id: " + mainViewModel.userId.value)
            startActivity(intentMyPagePost)
        }

        binding.textMyPageReplyByMe.setOnClickListener {
            val intentMyPageReply = Intent(requireActivity(), MyPageReplyActivity::class.java)
            intentMyPageReply.putExtra("userId", mainViewModel.userId.value ?: 0)
            Log.d("mypageIntentReply userId - ", "id : " + mainViewModel.userId.value)
            startActivity(intentMyPageReply)
        }

        binding.textMyPageReview.setOnClickListener {
            val intentMyPageReview =
                Intent(requireActivity(), MyPageClassroomReviewActivity::class.java)
            startActivity(intentMyPageReview)
        }

        binding.clMyPageHeartList.setOnClickListener {
            val intentHeartList = Intent(requireActivity(), MyPageLikeListActivity::class.java)
            startActivity(intentHeartList)
        }

        binding.imgMyPageSetting.setOnClickListener {
            mainViewModel.mypageFragmentNum.value = 1
        }


        binding.clMyPageProfileModify.setOnClickListener {
            val intentMyPageModify = Intent(requireActivity(), ModifyMyInfoActivity::class.java)

            intentMyPageModify.putExtra("nickname", binding.myPageInfo?.data?.nickname)
            intentMyPageModify.putExtra("firstMajor", binding.myPageInfo?.data?.firstMajorName)
            intentMyPageModify.putExtra("firstMajorStart", binding.myPageInfo?.data?.firstMajorStart)
            intentMyPageModify.putExtra("secondMajor", binding.myPageInfo?.data?.secondMajorName)
            intentMyPageModify.putExtra("secondMajorStart", binding.myPageInfo?.data?.secondMajorStart)

            startActivity(intentMyPageModify)
        }

    }


    private fun initAskPersonal() {
        //마이페이지 선배 1:1
        mainViewModel.signData.observe(viewLifecycleOwner){
            myPageViewModel.getMyPageQuestion(it.userId)
        }
        myPageQuestionAdapter = MyPageMainAdapter(2, mainViewModel.userId.value ?: 0,1)
        binding.rcMyPageQuestion.adapter = myPageQuestionAdapter
        myPageViewModel.personalQuestion.observe(viewLifecycleOwner) {
            myPageQuestionAdapter.setQuestionMain((it.data.classroomPostList) as MutableList<MyPageQuestionData.Data.ClassroomPost>)

        }
    }

    //내 정보 서버통신
    private fun initPersonalInfo() {
        mainViewModel.signData.observe(viewLifecycleOwner) {
            myPageViewModel.getPersonalInfo(it.userId)
        }

        myPageViewModel.getPersonalInfo(mainViewModel.userId.value ?: 0)
        myPageViewModel.personalInfo.observe(viewLifecycleOwner){
            binding.myPageInfo = it
            if(it.data.secondMajorName == "미진입")
                binding.textMyPageSecondMajorTime.visibility = View.GONE
        }
    }

}






