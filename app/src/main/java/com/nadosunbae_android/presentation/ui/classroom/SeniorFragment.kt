package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomSeniorData
import com.nadosunbae_android.databinding.FragmentSeniorBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomSeniorOffAdapter
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomSeniorOnAdapter
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.util.CustomDialog


class SeniorFragment : BaseFragment<FragmentSeniorBinding>(R.layout.fragment_senior) {
    private lateinit var classRoomSeniorOnAdapter : ClassRoomSeniorOnAdapter
    private lateinit var classRoomSeniorOffAdapter : ClassRoomSeniorOffAdapter
    var link = DataToFragment()

    private val mainViewModel: MainViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }
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
        classRoomSeniorOffAdapter = ClassRoomSeniorOffAdapter()
        binding.rcSeniorQuestionOff.adapter = classRoomSeniorOffAdapter
        binding.rcSeniorQuestionOn.adapter = classRoomSeniorOnAdapter
        mainViewModel.seniorData.observe(viewLifecycleOwner){
            classRoomSeniorOnAdapter.setOnQuestionUser(it.onQuestionUserList as MutableList<ResponseClassRoomSeniorData.Data.OnQuestionUser>)
            classRoomSeniorOffAdapter.setOffQuestionUser(it.offQuestionUserList as MutableList<ResponseClassRoomSeniorData.Data.OffQuestionUser>)
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