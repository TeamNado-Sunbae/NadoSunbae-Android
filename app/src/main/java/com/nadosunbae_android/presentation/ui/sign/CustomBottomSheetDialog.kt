package com.nadosunbae_android.presentation.ui.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.R

import androidx.databinding.DataBindingUtil
import com.nadosunbae_android.data.model.sign.ResponseMajorData
import com.nadosunbae_android.databinding.FragmentCustomBottomSheetDialogBinding
import com.nadosunbae_android.presentation.ui.sign.adapter.SignSelectionAdapter


class CustomBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var signSelectionAdapter: SignSelectionAdapter
    private lateinit var _binding : FragmentCustomBottomSheetDialogBinding
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_bottom_sheet_dialog,container, false)

        adapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clCustomBottomSheet.layoutParams.height = resources.displayMetrics.heightPixels * 72/100
    }

    private fun adapter() {
        signSelectionAdapter = SignSelectionAdapter()
        binding.rlBottomsheet.adapter = signSelectionAdapter
        var signSelectionData = mutableListOf(
            ResponseMajorData.Data.Major(1,"고려대학교"),
            ResponseMajorData.Data.Major(2,"고려대학교1")
        )

        signSelectionAdapter.signSelectionData.addAll(
            signSelectionData
        )

        signSelectionAdapter.notifyDataSetChanged()
    }

}