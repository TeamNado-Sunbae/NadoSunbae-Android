package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.util.Log
import android.view.View
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.classroom.ClassRoomData
import com.nadosunbae_android.databinding.FragmentInformationBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomInfoMainAdapter
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information) {
    private lateinit var classRoomInfoMainAdapter : ClassRoomInfoMainAdapter
    private val mainViewModel: MainViewModel by sharedViewModel()

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
           Log.d("classRoomInfo", it.toString())
           classRoomInfoMainAdapter.setQuestionMain(it as MutableList<ClassRoomData>)
       }


    }
}