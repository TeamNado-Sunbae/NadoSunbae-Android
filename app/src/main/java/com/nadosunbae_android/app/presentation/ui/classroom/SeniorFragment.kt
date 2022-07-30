package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentSeniorBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomSeniorOffAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomSeniorOnAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.question.DataToFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SeniorFragment : BaseFragment<FragmentSeniorBinding>(R.layout.fragment_senior) {
    private lateinit var classRoomSeniorOnAdapter : ClassRoomSeniorOnAdapter
    private lateinit var classRoomSeniorOffAdapter : ClassRoomSeniorOffAdapter
    var link = SeniorDataToFragment()

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSenior()
        goQuestionFragment()
        changeTitle()
        observeLoadingEnd()
    }

    //로딩 종료
    private fun observeLoadingEnd() {
        mainViewModel.onLoadingEnd.observe(viewLifecycleOwner){
            dismissLoading()
        }
    }

    // 구성원 보여주기
    private fun initSenior(){
        showLoading()
        classRoomSeniorOnAdapter = ClassRoomSeniorOnAdapter(link)
        classRoomSeniorOffAdapter = ClassRoomSeniorOffAdapter(link)
        binding.rcSeniorQuestionOff.adapter = classRoomSeniorOffAdapter
        binding.rcSeniorQuestionOn.adapter = classRoomSeniorOnAdapter
        mainViewModel.seniorData.observe(viewLifecycleOwner) {
            classRoomSeniorOnAdapter.setOnQuestionUser(it.onQuestionUserList as MutableList<ClassRoomSeniorData.OnQuestionUser>)
            classRoomSeniorOffAdapter.setOffQuestionUser(it.offQuestionUserList as MutableList<ClassRoomSeniorData.OffQuestionUser>)
        }

    }
    //선배 Id = userId가 같을 경우 마이페이지로 이동
    private fun goMyPage(seniorId : Int){
        val userId = mainViewModel.userId.value ?: 0
        Timber.d("userId : $userId")
        Timber.d("seniorId : $seniorId")
        if(userId == seniorId){
            mainViewModel.bottomNavItem.value = 4
        }else{
            mainViewModel.classRoomFragmentNum.value = 4
            mainViewModel.initLoading.value = true
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
    inner class SeniorDataToFragment : DataToFragment {
        override fun getSeniorId(seniorId: Int){
            mainViewModel.seniorId.value = seniorId
            goMyPage(seniorId)
        }
    }
}