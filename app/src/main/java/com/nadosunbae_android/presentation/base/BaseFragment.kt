package com.nadosunbae_android.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.nadosunbae_android.data.model.response.sign.BottomSheetData
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.sign.CustomBottomSheetDialog

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment() {
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observeBottomSheet(viewModel: MainViewModel, majorBottomSheetDialog: CustomBottomSheetDialog) {
        viewModel.majorList.observe(viewLifecycleOwner) {
            val responseData = viewModel.majorList.value?.data
            val dialogInput = mutableListOf<BottomSheetData>()

            // null check
            if (responseData != null) {
                for (d in responseData)
                    dialogInput.add(BottomSheetData(d.majorId, d.majorName, false))
            }

            majorBottomSheetDialog.setDataList(dialogInput)
        }
    }
}