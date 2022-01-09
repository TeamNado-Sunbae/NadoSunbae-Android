package com.nadosunbae_andorid.presentation.ui.classroom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.FragmentClassRoomBinding
import com.nadosunbae_andorid.presentation.base.BaseFragment
import com.nadosunbae_andorid.presentation.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


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
        Log.d("classRoom","왜 안나오냐")
    }





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
            }else{
                binding.textClassroomInfoTitle.isSelected = true
                binding.textClassroomQuestionTitle.isSelected = false
            }
        }
    }
}