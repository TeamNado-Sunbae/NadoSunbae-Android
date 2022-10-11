package com.nadosunbae_android.app.util


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentCustomBottomSheetDialogBinding
import com.nadosunbae_android.app.presentation.ui.sign.adapter.MajorSelectAdapter
import com.nadosunbae_android.domain.model.main.SelectableData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class CustomBottomSheetDialog(
    private val title: String,
    private val checkCommunity: Boolean? = false,
    noMajor: Int? = 0,
    checkCommunityWrite: Boolean? = false,
    private var isSignUp : Boolean ?= false
) : BottomSheetDialogFragment() {



    // 바텀시트 타이틀
    private var _titleData = MutableLiveData<String>()
    val titleData: LiveData<String>
        get() = _titleData

    var completeOperation: () -> Unit = { }

    //바텀 시트 majorId
    private var _majorId = MutableLiveData<Int>()
    val majorId: LiveData<Int>
        get() = _majorId

    private var majorSelectAdapter: MajorSelectAdapter

    //학과 데이터
    private var majorData = mutableListOf<SelectableData>()

    //debounce 학과 검색
    private val debounceAction = debounce<String>(200,
        CoroutineScope(Dispatchers.Main),
        block = {
            setFilterData(it)
        }
    )


    private lateinit var _binding: FragmentCustomBottomSheetDialogBinding
    val binding get() = _binding!!

    init {
        majorSelectAdapter = MajorSelectAdapter(noMajor, checkCommunityWrite, isSignUp)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

        }
        dialog.setOnShowListener {
            val behavior = BottomSheetBehavior.from(binding.clCustomBottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
        }

        return dialog
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
        binding.crCustomBottomSheet.layoutParams.height =
            resources.displayMetrics.heightPixels * 72 / 100

        binding.tvBottomsheeetTitle.text = title
        searchFilterMajor()
        observeFavoritesData()
        getFavoritesData()
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
                try {
                    activity?.supportFragmentManager!!.beginTransaction().remove(this).commit()
                } catch (e: Exception) {
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

    //필터 학과 검색시
    private fun searchFilterMajor() {
        binding.etBottomSheetSearch.addTextChangedListener {
            debounceAction(
                it.toString()
            )
        }
    }

    //필터 데이터 변경
    private fun setFilterData(filter: String) {
        if (filter.isEmpty()) {
            majorSelectAdapter.submitList(majorData) {
                setScrollTop()
            }
        } else {
            val filterData = majorData.filter { it.name.contains(filter) }
            majorSelectAdapter.submitList(filterData) {
                setScrollTop()
            }
        }
    }

    //스크롤 맨 위로
    private fun setScrollTop() {
        binding.rvBottomSheet.apply {
            post { scrollToPosition(0) }
        }
    }

    private fun initAdapter() {
        // Recycler view 구분선 추가
        val decoration =
            CustomDecoration(1.dpToPxF, 0.0f, requireContext().getColor(R.color.gray_1))
        binding.rvBottomSheet.addItemDecoration(decoration)

        binding.rvBottomSheet.adapter = majorSelectAdapter
    }

    //선택 데이터 체관
    private fun observeSelectedData() {
        majorSelectAdapter.selectedData.observe(viewLifecycleOwner) {
            if (checkCommunity == true) {
                binding.btnBottomsheetComplete.isEnabled = true
            } else {
                binding.btnBottomsheetComplete.isEnabled =
                    majorSelectAdapter.selectedData.value!!.isSelected
            }
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
        majorData = dataList
        majorSelectAdapter.submitList(dataList)
    }


    //바텀 시트 선택 데이터
    fun getSelectedData(): SelectableData {
        return majorSelectAdapter.selectedData.value ?: SelectableData.DEFAULT
    }

    //학과 즐겨찾기 데이터
    private fun getFavoritesData() {
        majorSelectAdapter.setFavoritesClickListener {
            _majorId.value = it
        }
    }

    //즐겨찾기 데이터 옵저빙시
    fun observeFavoritesData() {
        majorId.observe(viewLifecycleOwner) { id ->
            completeFavorites.let {
                it(id)
            }
        }

    }

    private var completeFavorites: (Int) -> Unit = {}

    fun setCompleteFavoritesListener(operation: (Int) -> Unit) {
        this.completeFavorites = operation
    }

    //첫 선택된 데이터
    fun setSelectedData(dataId: Int) {
        majorSelectAdapter.setSelectedData(dataId)
    }


}
