package com.nadosunbae_android.app.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.major.MajorListData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment() {
    private var _binding: T? = null
    val binding get() = _binding!!

    protected var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //데이터 넣기
    fun observeBottomSheet(viewModel: MainViewModel, majorBottomSheetDialog: CustomBottomSheetDialog) {
        viewModel.majorList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach{
            val responseData = it
            val dialogInput = mutableListOf<SelectableData>()

            // null check
            if (responseData != null) {
                for (d in responseData)
                    dialogInput.add(SelectableData(d.majorId, d.majorName,false, d.isFavorites))
            }
                Timber.d("dialog $dialogInput")
            majorBottomSheetDialog.setDataList(dialogInput)
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    //데이터 넣기
    fun observeBottomSheet2(majorList : List<MajorListData>, majorBottomSheetDialog: CustomBottomSheetDialog) {
        val dialogInput = mutableListOf<SelectableData>()
        // null check
        if (majorList != null) {
            for (d in majorList)
                dialogInput.add(SelectableData(d.majorId, d.majorName, false, d.isFavorites))
        }
        majorBottomSheetDialog.setDataList(dialogInput)
    }


    protected fun showLoading() {
        dismissLoading()
        loadingDialog = CustomDialog(requireContext()).progressDialog()
    }

    protected fun dismissLoading() {
        if (loadingDialog != null)
            loadingDialog!!.dismiss()
    }


}