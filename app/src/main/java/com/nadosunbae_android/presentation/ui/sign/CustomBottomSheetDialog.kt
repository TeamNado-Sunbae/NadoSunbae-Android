package com.nadosunbae_android.presentation.ui.sign

import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.R

import androidx.databinding.DataBindingUtil
import com.nadosunbae_android.data.model.sign.BottomSheetData
import com.nadosunbae_android.data.model.sign.ResponseMajorData
import com.nadosunbae_android.databinding.FragmentCustomBottomSheetDialogBinding
import com.nadosunbae_android.presentation.ui.sign.adapter.MajorSelectAdapter
import com.nadosunbae_android.util.CustomDecoration


class CustomBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var majorSelectAdapter: MajorSelectAdapter
    private lateinit var _binding : FragmentCustomBottomSheetDialogBinding
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_bottom_sheet_dialog,container, false)

        initAdapter()
        setClickListener()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clCustomBottomSheet.layoutParams.height = resources.displayMetrics.heightPixels * 72/100
    }

    private fun setClickListener() {
        binding.btnBottomsheetCancel.setOnClickListener {
            activity?.supportFragmentManager!!.beginTransaction().remove(this).commit()
        }
    }


    private fun initAdapter() {
        // Recycler view 구분선 추가
        val decoration = CustomDecoration(1.0f, 0.0f, requireContext().getColor(R.color.gray_1))
        binding.rvBottomsheet.addItemDecoration(decoration)

        majorSelectAdapter = MajorSelectAdapter()
        binding.rvBottomsheet.adapter = majorSelectAdapter


        var signSelectionData = mutableListOf(
            BottomSheetData(1,"22-1", false),
            BottomSheetData(2,"21-2", false),
            BottomSheetData(3,"21-1", false),
            BottomSheetData(4,"20-2", false),
            BottomSheetData(5,"20-1", false),
            BottomSheetData(6,"19-2", false),
            BottomSheetData(7,"19-1", false),
            BottomSheetData(8,"18-2", false),
            BottomSheetData(9,"18-1", false),
            BottomSheetData(10,"17-2", false),
            BottomSheetData(11,"17-1", false),
            BottomSheetData(12,"16-2", false),
            BottomSheetData(13,"16-1", false),
            BottomSheetData(14,"15-2", false),
            BottomSheetData(15,"15-1", false),
            BottomSheetData(16,"15년 이전", false),

        )

        majorSelectAdapter.dataList.addAll(
            signSelectionData
        )

        majorSelectAdapter.notifyDataSetChanged()
    }

}