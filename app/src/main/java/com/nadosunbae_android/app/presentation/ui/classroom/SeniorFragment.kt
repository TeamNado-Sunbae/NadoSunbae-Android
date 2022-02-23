package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.util.Log
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentSeniorBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomSeniorOffAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomSeniorOnAdapter
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SeniorFragment : BaseFragment<FragmentSeniorBinding>(R.layout.fragment_senior) {
    private lateinit var classRoomSeniorOnAdapter : ClassRoomSeniorOnAdapter
    private lateinit var classRoomSeniorOffAdapter : ClassRoomSeniorOffAdapter
    var link = DataToFragment()

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSenior()
        goQuestionFragment()
        changeTitle()
    }


    // 구성원 보여주기
    private fun initSenior(){
        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
            mainViewModel.getClassRoomSenior(it.majorId)
        }

        classRoomSeniorOnAdapter = ClassRoomSeniorOnAdapter(link)
        classRoomSeniorOffAdapter = ClassRoomSeniorOffAdapter(link)
        binding.rcSeniorQuestionOff.adapter = classRoomSeniorOffAdapter
        binding.rcSeniorQuestionOn.adapter = classRoomSeniorOnAdapter
        mainViewModel.seniorData.observe(viewLifecycleOwner){
            classRoomSeniorOnAdapter.setOnQuestionUser(it.onQuestionUserList as MutableList<ClassRoomSeniorData.OnQuestionUser>)
            classRoomSeniorOffAdapter.setOffQuestionUser(it.offQuestionUserList as MutableList<ClassRoomSeniorData.OffQuestionUser>)
        }

    }


    //뒤로가기
    private fun goQuestionFragment(){
        binding.imgSeniorTitle.setOnClickListener {
            mainViewModel.classRoomBackFragmentNum.value = 2
        }
    }

    //타이틀 변경
    private fun changeTitle(){
        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
            binding.textSeniorTitle.text = it.majorName
        }
    }
    inner class DataToFragment{
        fun getSeniorId(seniorNum : Int, seniorId : Int){
            mainViewModel.classRoomFragmentNum.value = seniorNum
            mainViewModel.seniorId.value = seniorId
            Log.d("seniorId", seniorId.toString())
        }
    }
}