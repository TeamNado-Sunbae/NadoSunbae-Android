package com.nadosunbae_android.presentation.ui.review

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.R
import androidx.databinding.DataBindingUtil
import com.nadosunbae_android.databinding.FragmentFilterBottomSheetBinding
import com.nadosunbae_android.util.finish

class FilterBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var _binding : FragmentFilterBottomSheetBinding
    val binding get() = _binding!!

    private var filterButtonList = mutableListOf<Button>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_bottom_sheet,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFilterButtonList()
        setClickListener()
    }

    private fun initFilterButtonList() {
        filterButtonList.addAll(
            mutableListOf(
                binding.btnFilterFirstMajor,
                binding.btnFilterSecondMajor,
                binding.btnFilterCurriculum,
                binding.btnFilterRecommendLecture,
                binding.btnFilterNonRecommendLecture,
                binding.btnFilterCareer,
                binding.btnFilterTip
            )
        )
    }

    private fun resetFilters() {
        for (btn in filterButtonList) {
            btn.isSelected = false
        }
    }

    // 적용하기 버튼 활성화 로직
    private fun setApplyButton() {
        var validApply = false

        for (btn in filterButtonList) {
            if (btn.isSelected)
                validApply = true
        }

        binding.btnFilterApply.isEnabled = validApply
    }

    private fun setClickListener() {

        // 필터 초기화 버튼
        binding.btnFilterReset.setOnClickListener {
            resetFilters()
            setApplyButton()
        }

        // 필터 버튼들 선택 리스너
        for (btn in filterButtonList) {
            btn.setOnClickListener {
                btn.isSelected = !btn.isSelected
                setApplyButton()
            }
        }

        // 적용하기 버튼
        binding.btnFilterApply.setOnClickListener {
            Log.d("TEST", "Filter Apply")
        }

        // 닫기 버튼
        binding.btnBottomsheetCancel.setOnClickListener {
            finish()
        }
    }


}