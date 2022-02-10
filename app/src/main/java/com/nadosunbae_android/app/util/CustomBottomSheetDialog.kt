package com.nadosunbae_android.app.util


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.app.databinding.FragmentCustomBottomSheetDialogBinding
import com.nadosunbae_android.app.presentation.ui.sign.adapter.MajorSelectAdapter
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel


class CustomBottomSheetDialog(private val title: String) : BottomSheetDialogFragment() {

    private val signViewModel: SignViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignViewModel() as T
            }
        }
    }

    // 바텀시트 타이틀
    private var _titleData = MutableLiveData<String>()
    val titleData: LiveData<String>
        get() = _titleData

    var completeOperation: () -> Unit = { }

    private var majorSelectAdapter: MajorSelectAdapter
    private lateinit var _binding: FragmentCustomBottomSheetDialogBinding
    val binding get() = _binding!!
    var link = DataToFragment()

    init {
        majorSelectAdapter = MajorSelectAdapter(link)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_custom_bottom_sheet_dialog,
            container,
            false
        )

        initTitle()
        initAdapter()
        setClickListener()
        observeSelectedData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clCustomBottomSheet.layoutParams.height =
            resources.displayMetrics.heightPixels * 72 / 100
        binding.tvBottomsheeetTitle.text = title
        binding.executePendingBindings()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        majorSelectAdapter.clearSelect()
    }

    private fun setClickListener() {
        binding.btnBottomsheetCancel.setOnClickListener {
            activity?.supportFragmentManager!!.beginTransaction().remove(this).commit()
        }
        binding.fragment = this
    }

    //타이틀 왼쪽에 표시되는 제목
    private fun initTitle() {
        titleData.observe(viewLifecycleOwner) {
            binding.tvBottomsheeetTitle.text = titleData.value
        }
        _titleData.value = title
    }


    private fun initAdapter() {
        // Recycler view 구분선 추가
        val decoration =
            CustomDecoration(1.dpToPxF, 0.0f, requireContext().getColor(R.color.gray_1))
        binding.rvBottomsheet.addItemDecoration(decoration)

        binding.rvBottomsheet.adapter = majorSelectAdapter
    }

    private fun observeSelectedData() {
        majorSelectAdapter.selectedData.observe(viewLifecycleOwner) {
            binding.btnBottomsheetComplete.isEnabled =
                majorSelectAdapter.selectedData.value!!.isSelected
        }
    }


    fun setCompleteListener(operation: () -> Unit) {
        completeOperation = operation
    }

    fun completeBtnListener(view: View) {
        completeOperation()
        dismiss()
    }

    fun setDataList(dataList: MutableList<SelectableData>) {
        majorSelectAdapter.dataList.addAll(dataList)
        majorSelectAdapter.notifyDataSetChanged()
    }

    fun getSelectedData(): SelectableData? {
        return majorSelectAdapter.selectedData.value
    }

    fun setSelectedData(dataId: Int) {
        majorSelectAdapter.setSelectedData(dataId)
    }


    inner class DataToFragment() {
        fun getBtnSelector(bool: Boolean) {
            binding.btnBottomsheetComplete.isSelected = bool

        }




    }
}

