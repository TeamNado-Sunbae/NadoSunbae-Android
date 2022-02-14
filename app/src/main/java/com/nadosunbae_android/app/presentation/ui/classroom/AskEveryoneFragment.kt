package com.nadosunbae_android.app.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentAskEveryoneBinding
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomAskEveryoneAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.AskEveryOneViewModel
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.main.SelectableData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AskEveryoneFragment : BaseFragment<FragmentAskEveryoneBinding>(R.layout.fragment_ask_everyone) {
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val askEveryOneViewModel : AskEveryOneViewModel by viewModel()
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
            classRoomAskEveryoneAdapter.setAskEveryone(it as MutableList<ClassRoomData>)
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

    //최신순, 도움순 정렬
    private fun questionSort(){
        binding.textAskEveryoneArray.setOnClickListener {
            val questionDropDownList = mutableListOf<SelectableData>(
            SelectableData(1, getString(R.string.review_latest_order), true),
            SelectableData(2, getString(R.string.review_likes_order), false)
        )
            showCustomDropDown(askEveryOneViewModel, binding.textAskEveryoneArray, 160f.dpToPx, askEveryOneViewModel.dropDownSelected.value!!.id, questionDropDownList)
        }

    }
}