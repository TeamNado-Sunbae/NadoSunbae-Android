package com.nadosunbae_android.app.util


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentCustomBottomSheetDialogBinding
import com.nadosunbae_android.app.presentation.ui.sign.adapter.MajorSelectAdapter
import com.nadosunbae_android.domain.model.main.SelectableData


class CustomBottomSheetDialog(
    private val title: String,
    private var checkCommunity: Boolean? = false,
    noMajor : Int? = 0
) : BottomSheetDialogFragment() {


    // 바텀시트 타이틀
    private var _titleData = MutableLiveData<String>()
    val titleData: LiveData<String>
        get() = _titleData

    var completeOperation: () -> Unit = { }

    private var majorSelectAdapter: MajorSelectAdapter

    private lateinit var _binding: FragmentCustomBottomSheetDialogBinding
    val binding get() = _binding!!

    init {
        majorSelectAdapter = MajorSelectAdapter(noMajor)
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
        initBottomSheetSetting()
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

    private fun initBottomSheetSetting() {
        binding.btnBottomsheetComplete.isEnabled = checkCommunity ?: false
    }

    //커뮤니티일때 종료 다르게
    private fun setClickListener() {
        binding.btnBottomsheetCancel.setOnClickListener {
            if (checkCommunity == true) {
                dismiss()
            } else {
                try{
                    activity?.supportFragmentManager!!.beginTransaction().remove(this).commit()
                } catch(e : Exception) {
                    dismiss()
                }

            }
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
        binding.rvBottomSheet.addItemDecoration(decoration)

        binding.rvBottomSheet.adapter = majorSelectAdapter
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

    fun completeBtnListener(view : View) {
        completeOperation()
        dismiss()
    }

    fun setDataList(dataList: MutableList<SelectableData>) {
        majorSelectAdapter.dataList.addAll(dataList)
        majorSelectAdapter.notifyDataSetChanged()
    }

    //바텀 시트 선택 데이터
    fun getSelectedData(): SelectableData {
        return majorSelectAdapter.selectedData.value ?: SelectableData.DEFAULT
    }

    //첫 선택된 데이터
    fun setSelectedData(dataId: Int) {
        majorSelectAdapter.setSelectedData(dataId)
    }





}
