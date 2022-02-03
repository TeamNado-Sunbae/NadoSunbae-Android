package com.nadosunbae_android.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.databinding.FragmentSeniorPersonalBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomQuestionMainAdapter
import com.nadosunbae_android.presentation.ui.classroom.viewmodel.SeniorPersonalViewModel
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.mapper.classroom.ClassRoomMapper
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeniorPersonalFragment :
    BaseFragment<FragmentSeniorPersonalBinding>(R.layout.fragment_senior_personal) {
    private lateinit var classRoomQuestionMainAdapter: ClassRoomQuestionMainAdapter
    private lateinit var callback: OnBackPressedCallback

    private val mainViewModel: MainViewModel by sharedViewModel()

    private val seniorPersonalViewModel: SeniorPersonalViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initSeniorQuestion()
        goClassRoomReview()
        getSeniorPersonal()
        initSeniorQuestion()
        goSeniorFragment()
        goQuestionWrite()
    }

    //선배에게 온 1:1 질문 목록
    private fun initSeniorQuestion() {

        classRoomQuestionMainAdapter = ClassRoomQuestionMainAdapter(2, mainViewModel.userId.value ?: 0,0)
        binding.rcSeniorPersonal.adapter = classRoomQuestionMainAdapter
        seniorPersonalViewModel.seniorQuestion.observe(viewLifecycleOwner) {
            Log.d("seniorQuestionAdapter", "좀 되라")
            classRoomQuestionMainAdapter.setQuestionMain(it as MutableList<ClassRoomData>)
        }


    }

    //리뷰 보러가기기
    private fun goClassRoomReview() {
        binding.imgSeniorPersonalClassReviewArrow.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 5
        }
    }

    //선배 개인페이지 서버 통신
    private fun getSeniorPersonal() {
        mainViewModel.seniorId.observe(viewLifecycleOwner) {
            Log.d("seniorId", it.toString())
            seniorPersonalViewModel.getSeniorPersonal(it)
            seniorPersonalViewModel.getSeniorQuestionList(it, "recent")
        }

        seniorPersonalViewModel.seniorPersonal.observe(viewLifecycleOwner) {
            seniorPersonalViewModel.userId.value = it.userId
            binding.seniorPersonal = it
            if(it.secondMajorName == "미진입")
                binding.textSeniorPersonalSecondMajorStart.visibility = View.GONE
        }
    }

    //뒤로가기
    private fun goSeniorFragment(){
        binding.imgSeniorPersonalTitle.setOnClickListener {
            mainViewModel.classRoomBackFragmentNum.value = 1
        }

    }

    //작성창으로 이동
    private fun goQuestionWrite(){
        binding.btnGoQuestionWrite.setOnClickListener {
            val intent = Intent(requireActivity(), QuestionWriteActivity::class.java)
            intent.apply {
                putExtra("majorId", mainViewModel.majorId.value)
                putExtra("userId", seniorPersonalViewModel.userId.value)
                Log.d("answerId", seniorPersonalViewModel.userId.value.toString())
                putExtra("postTypeId", 4)
                putExtra("title", "1:1질문 작성")
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getSeniorPersonal()
    }
}