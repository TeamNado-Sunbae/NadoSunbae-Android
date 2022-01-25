package com.nadosunbae_android.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.model.ui.classroom.ClassRoomData
import com.nadosunbae_android.databinding.FragmentAskEveryoneBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomAskEveryoneAdapter
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.mapper.Mapper


class AskEveryoneFragment : BaseFragment<FragmentAskEveryoneBinding>(R.layout.fragment_ask_everyone) {
    private val mainViewModel: MainViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }

    private lateinit var classRoomAskEveryoneAdapter : ClassRoomAskEveryoneAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeClassRoom()
        initAskEveryone()
        goQuestionWrite()
    }


    //과방탭 메인으로 이동
    private fun changeClassRoom(){
        binding.imgAskEveroneTitle.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 1
        }
        binding.textAskEveryoneTitle.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 1
        }
    }

    //리사이클러뷰
    private fun initAskEveryone(){
        //majorId 넣음
        mainViewModel.majorId.observe(viewLifecycleOwner){
            mainViewModel.getClassRoomMain(3,it)
        }

        classRoomAskEveryoneAdapter = ClassRoomAskEveryoneAdapter()
        binding.rcAskEveryone.adapter = classRoomAskEveryoneAdapter
        mainViewModel.classRoomMain.observe(viewLifecycleOwner){
            classRoomAskEveryoneAdapter.setAskEveryone(Mapper.mapperToQuestionMain(it) as MutableList<ClassRoomData>)
        }

    }

    override fun onResume() {
        super.onResume()
        mainViewModel.majorId.observe(viewLifecycleOwner){
            mainViewModel.getClassRoomMain(3,it)
        }
    }

    //전체 질문 작성으로 이동
    private fun goQuestionWrite(){
        binding.btnGoQuestionWrite.setOnClickListener {
            val intent = Intent(requireActivity(), QuestionWriteActivity::class.java)
            intent.apply {
                putExtra("title", "전체에게 질문 작성")
                putExtra("postTypeId",3)
                putExtra("majorId", mainViewModel.majorId.value)
            }
            startActivity(intent)
        }
    }


}