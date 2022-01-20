package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.ui.classroom.ClassRoomData
import com.nadosunbae_android.databinding.FragmentInformationBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomInfoMainAdapter
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.util.Mapper


class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information) {
    private lateinit var classRoomInfoMainAdapter : ClassRoomInfoMainAdapter
    private val mainViewModel: MainViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInfoMain()
    }


   private fun initInfoMain(){
       mainViewModel.selectedMajor.observe(viewLifecycleOwner){
           mainViewModel.majorId.value = it.majorId
           mainViewModel.getClassRoomMain(2,it.majorId)
       }
        classRoomInfoMainAdapter = ClassRoomInfoMainAdapter()
        binding.rcClassroomInfo.adapter = classRoomInfoMainAdapter
       mainViewModel.classRoomMain.observe(viewLifecycleOwner){
           Log.d("classRoomInfo", it.data.toString())
           classRoomInfoMainAdapter.setQuestionMain(Mapper.mapperToQuestionMain(it) as MutableList<ClassRoomData>)
       }


    }
}