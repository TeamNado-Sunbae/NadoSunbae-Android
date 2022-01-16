package com.nadosunbae_andorid.presentation.ui.sign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_andorid.R
import android.app.Activity

import android.util.DisplayMetrics
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nadosunbae_andorid.data.model.sign.ResponseMajorData
import com.nadosunbae_andorid.data.model.sign.SelectorBottomSheetData
import com.nadosunbae_andorid.databinding.FragmentCustomBottomSheetDialogBinding
import com.nadosunbae_andorid.presentation.ui.sign.adapter.SignSelectionAdapter
import kotlin.math.sign


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

    fun adapter() {
        signSelectionAdapter = SignSelectionAdapter()
        binding.rlBottomsheet.adapter = signSelectionAdapter
        var signSelectionData = mutableListOf(
            ResponseMajorData.Data.Major(1,"22-1"),
            ResponseMajorData.Data.Major(2,"21-2"),
            ResponseMajorData.Data.Major(3,"21-1"),
            ResponseMajorData.Data.Major(4,"20-2"),
            ResponseMajorData.Data.Major(5,"20-1"),
            ResponseMajorData.Data.Major(6,"19-2"),
            ResponseMajorData.Data.Major(7,"19-1"),
            ResponseMajorData.Data.Major(8,"18-2"),
            ResponseMajorData.Data.Major(9,"18-1"),
            ResponseMajorData.Data.Major(10,"17-2"),
            ResponseMajorData.Data.Major(11,"17-1"),
            ResponseMajorData.Data.Major(12,"16-2"),
            ResponseMajorData.Data.Major(13,"16-1"),
            ResponseMajorData.Data.Major(14,"15-2"),
            ResponseMajorData.Data.Major(15,"15-1"),
            ResponseMajorData.Data.Major(16,"15년 이전"),

        )

        signSelectionAdapter.signSelectionData.addAll(
            signSelectionData
        )

        signSelectionAdapter.notifyDataSetChanged()
    }

}