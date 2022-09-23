package com.nadosunbae_android.app.presentation.ui.mypage

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentFilterBottomSheetBinding
import com.nadosunbae_android.app.databinding.FragmentModifyImgBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyImgBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var _binding : FragmentModifyImgBottomSheetBinding
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentModifyImgBottomSheetBinding.inflate(inflater, container, false).run {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_img_bottom_sheet,container, false)
        return binding.root

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener { dialogInterface ->
            ((dialogInterface as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View).apply {
                val behavior = BottomSheetBehavior.from(this)
                val layoutParams = this.layoutParams
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                this.layoutParams = layoutParams
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}