package com.nadosunbae_android.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.ui.MajorData
import com.nadosunbae_android.data.model.ui.classroom.ClassRoomData
import com.nadosunbae_android.databinding.FragmentQuestionBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomQuestionMainAdapter
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.util.CustomBottomSheetDialog
import com.nadosunbae_android.util.Mapper


class QuestionFragment : BaseFragment<FragmentQuestionBinding>(R.layout.fragment_question) {
    private val mainViewModel: MainViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }
    private lateinit var classRoomQuestionMainAdapter : ClassRoomQuestionMainAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initQuestionMain()
        changeAskEveryOne()
        changeSeniorFragment()
        goQuestionWriteAll()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
            mainViewModel.getClassRoomMain(2,it.majorId)
        }
    }

    //질문 메인 데이터 받아오기
    private fun initQuestionMain(){
        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
            mainViewModel.getClassRoomMain(2,it.majorId)
        }

        classRoomQuestionMainAdapter = ClassRoomQuestionMainAdapter(1)
        binding.rcQuestionAll.adapter = classRoomQuestionMainAdapter
        mainViewModel.classRoomMain.observe(viewLifecycleOwner){
            Log.d("cclassRoomMain", it.data.toString())
            classRoomQuestionMainAdapter.setQuestionMain(Mapper.mapperToQuestionMain(it) as MutableList<ClassRoomData>)
            visibleQuestion()
        }

    }

    //데이터 개수에 따라 뷰 보이기 설정
    private fun visibleQuestion(){
        if(classRoomQuestionMainAdapter.questionMainData.size == 0){
            with(binding){
                rcQuestionAll.visibility = View.GONE
                textQuestionAllGo.visibility = View.GONE
                imgQuestionAllGo.visibility = View.GONE
                textQuestionAllNoComment.visibility = View.VISIBLE
            }
        }else if(classRoomQuestionMainAdapter.questionMainData.size in 1..4){
            with(binding){
                rcQuestionAll.visibility = View.VISIBLE
                textQuestionAllGo.visibility = View.GONE
                imgQuestionAllGo.visibility = View.GONE
                textQuestionAllNoComment.visibility = View.GONE
            }
        }else{
            with(binding){
                rcQuestionAll.visibility = View.VISIBLE
                textQuestionAllGo.visibility = View.VISIBLE
                imgQuestionAllGo.visibility = View.VISIBLE
                textQuestionAllNoComment.visibility = View.GONE
            }
        }
    }

    //전체에게 질문으로 이동
    private fun changeAskEveryOne(){
        binding.textQuestionAllGo.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 2
        }
    }

    //질문 구성원 목록으로 이동
    private fun changeSeniorFragment(){
        binding.imgPersonalQuestion.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 3
        }
    }

    //전체 질문 작성으로 이동
    private fun goQuestionWriteAll(){
        binding.textQuestionWrite.setOnClickListener {
            val intent = Intent(requireActivity(), QuestionWriteActivity::class.java)
            startActivity(intent)
        }
    }


}