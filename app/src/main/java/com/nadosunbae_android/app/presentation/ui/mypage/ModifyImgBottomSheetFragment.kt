package com.nadosunbae_android.app.presentation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentModifyImgBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyImgBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var _binding : FragmentModifyImgBottomSheetBinding
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentModifyImgBottomSheetBinding.inflate(inflater, container, false).run {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_img_bottom_sheet,container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancelListener()
    }

    //X버튼 클릭 리스너
    private fun cancelListener() {
        binding.btnBottomsheetCancel.setOnClickListener { dismiss() }
    }


}