package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentClassRoomBinding
import com.nadosunbae_android.app.databinding.FragmentClassRoomQuestionBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.domain.model.main.MajorSelectData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassRoomQuestionFragment : BaseFragment<FragmentClassRoomQuestionBinding>(R.layout.fragment_class_room_question) {

    // main view model 초기화
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
    }

    private fun initBinding() {
        binding.mainViewModel = mainViewModel
    }


}