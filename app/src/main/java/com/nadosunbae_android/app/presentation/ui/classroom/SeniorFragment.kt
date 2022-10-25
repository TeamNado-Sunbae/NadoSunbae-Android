package com.nadosunbae_android.app.presentation.ui.classroom

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentSeniorBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomSeniorOffAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomSeniorOnAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.question.DataToFragment
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
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
    private lateinit var callback: OnBackPressedCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = mainViewModel
        initSenior()
        initListener()
        goQuestionFragment()
        changeTitle()
        observeLoadingEnd()
        loadServerData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
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
        classRoomSeniorOnAdapter = ClassRoomSeniorOnAdapter(link, false)
        classRoomSeniorOffAdapter = ClassRoomSeniorOffAdapter(link)
        binding.rcSeniorQuestionOff.adapter = classRoomSeniorOffAdapter
        binding.rcSeniorQuestionOn.adapter = classRoomSeniorOnAdapter
        mainViewModel.seniorData.observe(viewLifecycleOwner) {
            classRoomSeniorOnAdapter.setOnQuestionUser(it.onQuestionUserList as MutableList<ClassRoomSeniorData.UserSummaryData>)
            classRoomSeniorOffAdapter.setOffQuestionUser(it.offQuestionUserList as MutableList<ClassRoomSeniorData.UserSummaryData>)
        }

    }

    private fun initListener() {
        binding.btnSwitchQuestion.setOnClickListener {
            mainViewModel.viewReviewedSeniors.value = (it as Switch).isChecked
        }
        mainViewModel.viewReviewedSeniors.observe(requireActivity()) {
            loadServerData()
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
        mainViewModel.seniorBack.value = 4
    }

    private fun loadServerData() {
        val majorId = ReviewGlobals.selectedMajor?.majorId
        if (majorId != null) {
            mainViewModel.getClassRoomSenior(majorId)
        }
    }

    private fun goBack() {
        mainViewModel.bottomNavItem.value = MainActivity.CLASSROOM_NOBACK
    }

    //뒤로가기
    private fun goQuestionFragment(){
        binding.imgSeniorTitle.setOnClickListener {
            goBack()
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