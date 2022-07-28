package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentMyPageBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageMainAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var myPageQuestionAdapter: MyPageMainAdapter
    private lateinit var callback : OnBackPressedCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoadingDismiss()
        observeLoadingEnd()
        initAskPersonal()
        movePage()
        initPersonalInfo()
        submitAnalytics()
        Timber.d("실행되는 중")
    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
                dismissLoading()
        }
    }

    private fun initLoadingDismiss(){
        mainViewModel.initLoading.observe(viewLifecycleOwner){
            Timber.d("프로필 및 닉네임 클릭시 전환되는 MyPage")
            dismissLoading()
        }

    }

    //1:1질문 엠티뷰
    private fun initReviewEmpty(size : Int){
        if(size == 0){
            binding.textQuestionEmpty.visibility = View.VISIBLE
        }else{
            binding.textQuestionEmpty.visibility = View.GONE
        }
    }


    private fun movePage() {
        //내가 쓴 글
        binding.textMyPagePostByMe.setOnClickListener {
            showLoading()
            val intentMyPagePost = Intent(requireActivity(), MyPagePostActivity::class.java)
            intentMyPagePost.putExtra("userId", mainViewModel.userId.value ?: 0)
            startActivity(intentMyPagePost)
        }

        //내가 쓴 답글
        binding.textMyPageReplyByMe.setOnClickListener {
            showLoading()
            val intentMyPageReply = Intent(requireActivity(), MyPageReplyActivity::class.java)
            intentMyPageReply.putExtra("userId", mainViewModel.userId.value ?: 0)
            startActivity(intentMyPageReply)
        }

        //학과 후기
        binding.textMyPageReview.setOnClickListener {
            showLoading()
            val intentMyPageReview =
                Intent(requireActivity(), MyPageClassroomReviewActivity::class.java)
            intentMyPageReview.putExtra("userId", mainViewModel.userId.value ?: 0)
            intentMyPageReview.putExtra("userNickName", binding.myPageInfo?.data?.nickname)

            startActivity(intentMyPageReview)
        }

        //좋아요 한 글
        binding.clMyPageHeartList.setOnClickListener {
            showLoading()
            val intentHeartList = Intent(requireActivity(), MyPageLikeListActivity::class.java)
            intentHeartList.putExtra("userId", mainViewModel.userId.value ?: 0)
            startActivity(intentHeartList)
        }

        //톱니바퀴
        binding.imgMyPageSetting.setOnClickListener {
            mainViewModel.mypageFragmentNum.value = 1
        }

        //내 정보 수정
        binding.clMyPageProfileModify.setOnClickListener {
            showLoading()
            val intentMyPageModify = Intent(requireActivity(), ModifyMyInfoActivity::class.java)
            intentMyPageModify.putExtra("id", mainViewModel.userId.value)
            startActivity(intentMyPageModify)
        }

    }

    override fun onResume() {
        super.onResume()
        initAskPersonal()
        initPersonalInfo()
        observeLoadingEnd()
    }

    private fun initAskPersonal() {
        //마이페이지 선배 1:1
        //showLoading()
        mainViewModel.signData.observe(viewLifecycleOwner) {
            myPageViewModel.getMyPageQuestion(it.userId)
        }

        myPageQuestionAdapter = MyPageMainAdapter(2, mainViewModel.userId.value ?: 0, 1)
        binding.rcMyPageQuestion.adapter = myPageQuestionAdapter
        myPageViewModel.personalQuestion.observe(viewLifecycleOwner) {
            initReviewEmpty(it.data.classroomPostList.size)
            myPageQuestionAdapter.setQuestionMain((it.data.classroomPostList) as MutableList<MyPageQuestionData.Data.ClassroomPost>)

        }
    }

    //내 정보 서버통신
    private fun initPersonalInfo() {
       // showLoading()
        mainViewModel.signData.observe(viewLifecycleOwner) {
            myPageViewModel.getPersonalInfo(it.userId)
        }

        myPageViewModel.getPersonalInfo(mainViewModel.userId.value ?: 0)
        myPageViewModel.personalInfo.observe(viewLifecycleOwner) {
            binding.myPageInfo = it

            if (it.data.secondMajorName == "미진입")
                binding.textMyPageSecondMajorTime.visibility = View.INVISIBLE
            else
                binding.textMyPageSecondMajorTime.visibility = View.VISIBLE

            if(!it.data.isOnQuestion) {
                binding.clMyPageMainQuestion.visibility = View.VISIBLE
            } else {
                binding.clMyPageMainQuestion.visibility = View.GONE
            }
        }
    }

    private fun submitAnalytics() {
        FirebaseAnalyticsUtil.selectTab(FirebaseAnalyticsUtil.Tab.MYPAGE)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(mainViewModel.bottomNavItem.value == 4){
                    mainViewModel.bottomNavItem.value = 3
                }else{
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}






