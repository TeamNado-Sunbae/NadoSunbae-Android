package com.nadosunbae_andorid.presentation.ui.classroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomSeniorData
import com.nadosunbae_andorid.databinding.FragmentSeniorBinding
import com.nadosunbae_andorid.presentation.base.BaseFragment
import com.nadosunbae_andorid.presentation.ui.classroom.adapter.ClassRoomSeniorOnAdapter
import retrofit2.Response


class SeniorFragment : BaseFragment<FragmentSeniorBinding>(R.layout.fragment_senior) {
    private lateinit var classRoomSeniorOnAdapter : ClassRoomSeniorOnAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSeniorOn()
    }







    private fun initSeniorOn(){
        classRoomSeniorOnAdapter = ClassRoomSeniorOnAdapter()
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
}