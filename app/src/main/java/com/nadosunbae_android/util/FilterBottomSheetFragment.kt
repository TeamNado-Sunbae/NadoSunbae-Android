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
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel.Companion.FILTER_ALL
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel.Companion.FILTER_FIRST_MAJOR
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel.Companion.FILTER_SECOND_MAJOR
import com.nadosunbae_android.util.finish

class FilterBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var _binding : FragmentFilterBottomSheetBinding
    val binding get() = _binding!!

    var applyOperation: () -> Unit = { }

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
        setApplyListener()
    }

    private fun setApplyListener() {
        // 적용하기 버튼
        binding.btnFilterApply.setOnClickListener {
            applyOperation()
            finish()
        }
    }

    fun getWriterFilter(): Int {
        if (binding.btnFilterFirstMajor.isSelected)
            return FILTER_FIRST_MAJOR
        if (binding.btnFilterSecondMajor.isSelected)
            return FILTER_SECOND_MAJOR
        return FILTER_ALL
    }

    fun getTagFilter(): List<Int> {
        val ret = mutableListOf<Int>()
        if (binding.btnFilterCurriculum.isSelected)
            ret.add(1)
        if (binding.btnFilterRecommendLecture.isSelected)
            ret.add(2)
        if (binding.btnFilterNonRecommendLecture.isSelected)
            ret.add(3)
        if (binding.btnFilterCareer.isSelected)
            ret.add(4)
        if (binding.btnFilterTip.isSelected)
            ret.add(5)
        return ret.toList()
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

        // 닫기 버튼
        binding.btnBottomsheetCancel.setOnClickListener {
            finish()
        }
    }


}