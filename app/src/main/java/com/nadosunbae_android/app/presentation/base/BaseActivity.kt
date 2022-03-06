package com.nadosunbae_android.app.presentation.base

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDialog

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!

    protected lateinit var firebaseAnalytics: FirebaseAnalytics
    protected var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)

        firebaseAnalytics = Firebase.analytics

        // 화면고정 (세로모드)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun observeBottomSheet(viewModel: MainViewModel, majorBottomSheetDialog: CustomBottomSheetDialog) {
        viewModel.majorList.observe(this) {
            val responseData = viewModel.majorList.value
            val dialogInput = mutableListOf<SelectableData>()

            // null check
            if (responseData != null) {
                for (d in responseData)
                    dialogInput.add(SelectableData(d.majorId, d.majorName, false))
            }

            majorBottomSheetDialog.setDataList(dialogInput)
        }
    }

    protected fun showLoading() {
        dismissLoading()
        loadingDialog = CustomDialog(this).progressDialog()
    }

    protected fun dismissLoading() {
        if (loadingDialog != null)
            loadingDialog!!.dismiss()
    }

    // 커스텀 이벤트 로그 (단일 파라미터)
    protected fun firebaseLog(event: String, paramKey: String, paramVal: String) {
        firebaseAnalytics.logEvent(event) {
            param(paramKey, paramVal)
        }
    }


}