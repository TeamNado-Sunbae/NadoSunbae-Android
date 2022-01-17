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
        initSeniorOn()
        initSeniorOff()
    }


    // 질문 가능한 구성원 보여주기
    private fun initSeniorOn(){
        classRoomSeniorOnAdapter = ClassRoomSeniorOnAdapter(link)
        binding.rcSeniorQuestionOn.adapter = classRoomSeniorOnAdapter
        var exampleData = mutableListOf(ResponseClassRoomSeniorData.Data.OnQuestionUser(
            isFirstMajor = true,
            isOnQuestion = true,
            majorStart = "18-1 진입",
            "뿌꾸뿌구",
            1
        ),
            ResponseClassRoomSeniorData.Data.OnQuestionUser(
                isFirstMajor = true,
                isOnQuestion = true,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                2
            ),
            ResponseClassRoomSeniorData.Data.OnQuestionUser(
                isFirstMajor = true,
                isOnQuestion = true,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                3
            ),
            ResponseClassRoomSeniorData.Data.OnQuestionUser(
                isFirstMajor = true,
                isOnQuestion = true,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                4
            ))
        classRoomSeniorOnAdapter.setOnQuestionUser(exampleData)
    }

    // 질문 불가능한 구성원 보여주기
    private fun initSeniorOff(){
        classRoomSeniorOffAdapter = ClassRoomSeniorOffAdapter()
        binding.rcSeniorQuestionOff.adapter = classRoomSeniorOffAdapter
        var exampleData = mutableListOf(ResponseClassRoomSeniorData.Data.OffQuestionUser(
            isFirstMajor = true,
            isOnQuestion = false,
            majorStart = "18-1 진입",
            "뿌꾸뿌구",
            1
        ),
            ResponseClassRoomSeniorData.Data.OffQuestionUser(
                isFirstMajor = true,
                isOnQuestion = false,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                2
            ),
            ResponseClassRoomSeniorData.Data.OffQuestionUser(
                isFirstMajor = true,
                isOnQuestion = false,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                3
            ),
            ResponseClassRoomSeniorData.Data.OffQuestionUser(
                isFirstMajor = true,
                isOnQuestion = false,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                4
            ),
            ResponseClassRoomSeniorData.Data.OffQuestionUser(
                isFirstMajor = true,
                isOnQuestion = false,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                3
            ),ResponseClassRoomSeniorData.Data.OffQuestionUser(
                isFirstMajor = true,
                isOnQuestion = false,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                3
            ),ResponseClassRoomSeniorData.Data.OffQuestionUser(
                isFirstMajor = true,
                isOnQuestion = false,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                3
            ),ResponseClassRoomSeniorData.Data.OffQuestionUser(
                isFirstMajor = true,
                isOnQuestion = false,
                majorStart = "18-1 진입",
                "뿌꾸뿌구",
                3
            ),)
        classRoomSeniorOffAdapter.setOffQuestionUser(exampleData)
    }

    inner class DataToFragment{
        fun getSeniorId(seniorId : Int){
            mainViewModel.classRoomFragmentNum.value = seniorId

        }
    }
}