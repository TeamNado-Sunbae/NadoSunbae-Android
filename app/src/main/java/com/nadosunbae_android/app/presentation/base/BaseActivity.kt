package com.nadosunbae_android.app.presentation.base

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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

    fun observeBottomSheet(viewModel: MainViewModel, majorBottomSheetDialog: CustomBottomSheetDialog) {
        viewModel.majorList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                val responseData = viewModel.majorList.value
                val dialogInput = mutableListOf<SelectableData>()

                // null check
                if (responseData != null) {
                    for (d in responseData)
                        dialogInput.add(SelectableData(d.majorId, d.majorName, false))
                }

                majorBottomSheetDialog.setDataList(dialogInput)
            }
            .launchIn(lifecycleScope)
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