package com.nadosunbae_android.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.ui.classroom.ClassRoomData
import com.nadosunbae_android.databinding.FragmentMyPageBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomQuestionMainAdapter
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.util.Mapper


class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MyPageViewModel() as T
            }
        }
    }

    private val mainViewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }

    private lateinit var myPageQuestionAdapter: ClassRoomQuestionMainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAskPersonal()
        movePage()
        initPersonalInfo()
    }


    private fun movePage() {
        binding.textMyPagePostByMe.setOnClickListener {
            val intentMyPagePost = Intent(getActivity(), MyPagePostActivity::class.java)
            startActivity(intentMyPagePost)
        }

        binding.textMyPageReplyByMe.setOnClickListener {
            val intentMyPageReply = Intent(getActivity(), MyPageReplyActivity::class.java)
            startActivity(intentMyPageReply)
        }

        binding.textMyPageReview.setOnClickListener {
            val intentMyPageReview =
                Intent(getActivity(), MyPageClassroomReviewActivity::class.java)
            startActivity(intentMyPageReview)
        }

        binding.clMyPageHeartList.setOnClickListener {
            val intentHeartList = Intent(getActivity(), MyPageLikeListActivity::class.java)
            startActivity(intentHeartList)
        }
    }


    private fun initAskPersonal() {
        myPageViewModel.getMyPageQuestion(2, "recent")

        myPageQuestionAdapter = ClassRoomQuestionMainAdapter(2)
        binding.rcMyPageQuestion.adapter = myPageQuestionAdapter
        myPageViewModel.personalQuestion.observe(viewLifecycleOwner) {
            myPageQuestionAdapter.setQuestionMain(Mapper.mapperToMyPageQuestion(it) as MutableList<ClassRoomData>)
        }
//        mainViewModel.myId.observe(viewLifecycleOwner) {
//            myPageViewModel.getMyPageQuestion(it)
//            myPageViewModel.getMyPageQuestion(it, "recent")
//        }
//
//
//        myPageQuestionAdapter = ClassRoomQuestionMainAdapter(2)
//        binding.rcMyPageQuestion.adapter = myPageQuestionAdapter
//        myPageViewModel.personalQuestion.observe(viewLifecycleOwner) {
//            myPageQuestionAdapter.setQuestionMain(Mapper.mapperToMyPageQuestion(it) as MutableList<ClassRoomData>)
//        }

    }

    //내 정보 서버통신
    private fun initPersonalInfo() {
        myPageViewModel.getPersonalInfo()
        myPageViewModel.personalInfo.observe(viewLifecycleOwner){
            binding.myPageInfo = it
            if(it.data.secondMajorName == "미진입")
                binding.textMyPageSecondMajorTime.visibility = View.GONE
        }


    }
}






