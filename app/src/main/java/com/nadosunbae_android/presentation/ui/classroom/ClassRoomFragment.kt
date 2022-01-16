package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.FragmentClassRoomBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel


class ClassRoomFragment : BaseFragment<FragmentClassRoomBinding>(R.layout.fragment_class_room) {
    //메인뷰모델 초기화
    private val mainViewModel: MainViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectTitle()

    }




    //질문 정보 탭 클릭시에
    private fun selectTitle(){
        binding.textClassroomQuestionTitle.setOnClickListener {
            mainViewModel.classRoomNum.value = 1
        }
        binding.textClassroomInfoTitle.setOnClickListener {
            mainViewModel.classRoomNum.value = 2
        }

        mainViewModel.classRoomNum.observe(viewLifecycleOwner){
            if(it == 1){
                binding.textClassroomQuestionTitle.isSelected = true
                binding.textClassroomInfoTitle.isSelected = false
                binding.textClassroomQuestionTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_semibold)
                binding.textClassroomInfoTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_regular)
                changeFragment(QuestionFragment())
            }else{
                binding.textClassroomQuestionTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_regular)
                binding.textClassroomInfoTitle.typeface = ResourcesCompat.getFont(requireActivity(), R.font.pretendard_semibold)
                binding.textClassroomInfoTitle.isSelected = true
                binding.textClassroomQuestionTitle.isSelected = false
                changeFragment(InformationFragment())
            }
        }
    }




    //질문 정보 프래그먼트 변경
    private fun changeFragment(fragment : Fragment){
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_classroom, fragment)
            .commit()

    }


}