package com.nadosunbae_android.app.presentation.base

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.major.MajorListData

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!

    protected var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)

        // 화면고정 (세로모드)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun observeBottomSheet(
        majorList : List<MajorListData>,
        majorBottomSheetDialog: CustomBottomSheetDialog
    ) {
        val dialogInput = mutableListOf<SelectableData>()
        // null check
        if (majorList != null) {
            for (d in majorList)
                dialogInput.add(SelectableData(d.majorId, d.majorName, false))
        }
        majorBottomSheetDialog.setDataList(dialogInput)
    }


    protected fun showLoading() {
        dismissLoading()
        loadingDialog = CustomDialog(this).progressDialog()
    }

    protected fun dismissLoading() {
        if (loadingDialog != null)
            loadingDialog!!.dismiss()
    }


}