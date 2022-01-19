package com.nadosunbae_android.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun observeBottomSheet(viewModel: MainViewModel, majorBottomSheetDialog: CustomBottomSheetDialog) {
        viewModel.majorList.observe(this) {
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