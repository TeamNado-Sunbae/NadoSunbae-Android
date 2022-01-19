package com.nadosunbae_android.presentation.ui.sign


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.sign.BottomSheetData
import com.nadosunbae_android.databinding.FragmentCustomBottomSheetDialogBinding
import com.nadosunbae_android.presentation.ui.sign.adapter.MajorSelectAdapter
import com.nadosunbae_android.presentation.ui.sign.viewmodel.SignViewModel
import com.nadosunbae_android.util.CustomDecoration
import com.nadosunbae_android.util.dpToPxF


class CustomBottomSheetDialog : BottomSheetDialogFragment() {
    private val signViewModel: SignViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignViewModel() as T
            }
        }
    }

    private var majorSelectAdapter: MajorSelectAdapter
    private lateinit var _binding : FragmentCustomBottomSheetDialogBinding
    val binding get() = _binding!!
    var link = DataToFragment()

    init {
        majorSelectAdapter = MajorSelectAdapter(link)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_bottom_sheet_dialog,container, false)

        initAdapter()
        setClickListener()
        setCompleteBtnListener()

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

    private fun setCompleteBtnListener() {
        binding.btnBottomsheetComplete.setOnClickListener {
            activity?.supportFragmentManager!!.beginTransaction().remove(this).commit()

        }
    }


    private fun initAdapter() {
        // Recycler view 구분선 추가
        val decoration = CustomDecoration(1.dpToPxF, 0.0f, requireContext().getColor(R.color.gray_1))
        binding.rvBottomsheet.addItemDecoration(decoration)

        binding.rvBottomsheet.adapter = majorSelectAdapter
    }

    fun setDataList(dataList: MutableList<BottomSheetData>) {
        majorSelectAdapter.dataList.addAll(dataList)
        majorSelectAdapter.notifyDataSetChanged()
    }

    inner class DataToFragment(){
        fun getBtnSelector(bool : Boolean){
            binding.btnBottomsheetComplete.isSelected = bool

        }
        fun getEditTextSelector(string: String) {
            signViewModel.text.value = string
//            signViewModel.firstMajor.value = string
//            signViewModel.firstMajorPeriod.value = string
//            signViewModel.secondMajor.value = string
//            signViewModel.secondMajorPeriod.value = string
        }
    }
}