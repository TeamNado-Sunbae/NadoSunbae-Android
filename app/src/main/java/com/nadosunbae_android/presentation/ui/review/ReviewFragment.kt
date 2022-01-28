package com.nadosunbae_android.presentation.ui.review

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import com.nadosunbae_android.data.model.response.sign.SelectableData
import com.nadosunbae_android.data.model.ui.MajorData
import com.nadosunbae_android.databinding.FragmentReviewBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel.Companion.FILTER_ALL
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewListAdapter
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewListViewModel
import com.nadosunbae_android.util.CustomBottomSheetDialog
import com.nadosunbae_android.util.CustomDialog
import com.nadosunbae_android.util.dpToPx
import com.nadosunbae_android.util.showCustomDropDown

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private lateinit var reviewListAdapter : ReviewListAdapter

    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog
    private lateinit var filterBottomSheetDialog: FilterBottomSheetDialog

    private val mainViewModel: MainViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }

    private val reviewListViewModel: ReviewListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ReviewListViewModel() as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBinding()
        setStickyHeader()
        initReviewListAdapter()
        setReviewListData()
        setClickListener()
        initSortSelected()
        observeSelectedMajor()
        observePreviewList()
        observeFilter()
        observeSort()
        initBottomSheet()


    }

    override fun onResume() {
        super.onResume()

        loadReviewList()
    }

    private fun setBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel
        binding.reviewListViewModel = reviewListViewModel
    }



    private fun setReviewListData() {
        // reviewListViewModel observe (목록에 표시되도록)
        reviewListViewModel.reviewListData.observe(viewLifecycleOwner) {
            reviewListAdapter.setReviewListData(it.data as MutableList<ResponseReviewListData.Data>)
        }

    }




    private fun initReviewListAdapter() {
        reviewListAdapter = ReviewListAdapter()
        binding.rvReview.adapter = reviewListAdapter
    }

    private fun setClickListener() {

        // RecyclerView ItemClickListener
        reviewListAdapter.setItemClickListener(
            object: ReviewListAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {

                    // 후기 작성 여부 확인
                    val signData = mainViewModel.signData.value
                    if (!signData!!.isReviewed) {
                        // 후기 미작성시 알럿 띄우기
                        CustomDialog(requireContext())
                            .genericDialog(CustomDialog.DialogData(
                                getString(R.string.alert_no_review_title),
                                getString(R.string.alert_no_review_complete),
                                getString(R.string.alert_no_review_cancel)
                            ),
                            complete = {
                                   openReviewWrite()
                            },
                            cancel = {

                                }
                            )
                        return
                    }

                    // postId Intent로 전달 (후기 상세보기 이동)
                    val intent = Intent(context, ReviewDetailActivity::class.java)
                    val postId = reviewListViewModel.reviewListData.value!!.data[position].postId
                    intent.putExtra("postId", postId)
                    startActivity(intent)
                }

            }
        )

        binding.btnMajorPage.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(reviewListViewModel.urlHomepage.value))
            startActivity(intent)
        }

        binding.btnSubjectTable.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(reviewListViewModel.urlSubjectTable.value))
            startActivity(intent)
        }


        binding.btnWriteReview.setOnClickListener {
            openReviewWrite()
        }

        // 정렬 버튼
        binding.btnSort.setOnClickListener {
            val dropDownList = mutableListOf<SelectableData>(
                SelectableData(1, getString(R.string.review_latest_order), true),
                SelectableData(2, getString(R.string.review_likes_order), false)
            )


            showCustomDropDown(reviewListViewModel, binding.btnSort, 160f.dpToPx, reviewListViewModel.dropDownSelected.value!!.id, dropDownList)
        }

        val showMajorBottomSheetDialog = {
            majorBottomSheetDialog.show(parentFragmentManager, majorBottomSheetDialog.tag)

            // (학과 선택) 기본 선택값 적용 (MainActivity setDefaultMajor에서 관리)
            majorBottomSheetDialog.setSelectedData(mainViewModel.selectedMajor.value!!.majorId)
        }

        binding.btnSelectMajor.setOnClickListener { showMajorBottomSheetDialog() }
        binding.tvMajorSelected.setOnClickListener { showMajorBottomSheetDialog() }



        binding.btnReviewFilter.setOnClickListener {
            filterBottomSheetDialog.show(parentFragmentManager, filterBottomSheetDialog.tag)
        }

    }

    private fun setFilterApplyListener() {

        // 필터 적용 버튼 클릭 시
        filterBottomSheetDialog.applyOperation = {

            val writerFilter = filterBottomSheetDialog.getWriterFilter()
            val tagFilter = filterBottomSheetDialog.getTagFilter()

            mainViewModel.filterData.value = MainViewModel.FilterData(writerFilter, tagFilter)
        }

        // 필터 리셋 시
        filterBottomSheetDialog.resetFilterOperation = {
            mainViewModel.filterData.value = MainViewModel.FilterData(FILTER_ALL, listOf(1, 2, 3, 4, 5))
        }
    }

    private fun openReviewWrite() {
        val intent = Intent(context, ReviewWriteActivity::class.java)

        val selectedMajor = mainViewModel.selectedMajor.value
        val firstMajor = mainViewModel.firstMajor.value
        val secondMajor = mainViewModel.secondMajor.value
        // null check
        if (selectedMajor != null)
            intent.putExtra("selectedMajor", firstMajor)
        if (firstMajor != null)
            intent.putExtra("firstMajor", firstMajor)
        if (secondMajor != null)
            intent.putExtra("secondMajor", secondMajor)

        startActivity(intent)
    }

    private fun setStickyHeader() {
        binding.svReview.run {
            header = binding.clReviewFunctionBox
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }
    }

    private fun observePreviewList() {
        reviewListViewModel.previewList.observe(viewLifecycleOwner) {
            // recyclerView adaper에 적용
        }
    }

    private fun observeSelectedMajor() {
        // 선택 학과 observe
        mainViewModel.selectedMajor.observe(viewLifecycleOwner) {

            // null check
            if (mainViewModel.selectedMajor != null) {

                loadReviewList()
                // 선택된 학과 정보 불러오기
                reviewListViewModel.getMajorInfo(mainViewModel.selectedMajor.value!!.majorId)
            }
        }
    }

    private fun observeFilter() {

        mainViewModel.filterData.observe(viewLifecycleOwner) {
            val filter = mainViewModel.filterData.value

            // null check
            if (filter != null) {

                filterBottomSheetDialog.setFilter(filter)

                // 필터 활성화
                binding.btnReviewFilter.isSelected = !(filter.writerFilter == FILTER_ALL && filter.tagFilter == listOf(1, 2, 3, 4, 5))
                binding.executePendingBindings()

                // 후기 불러오기
                loadReviewList()

            }
        }

    }

    private fun observeSort() {
        reviewListViewModel.dropDownSelected.observe(viewLifecycleOwner) {
            val sortData = reviewListViewModel.dropDownSelected.value
            if (sortData != null) {
                if (sortData.id == 1)
                    binding.btnSort.text = getString(R.string.review_latest_order)
                else
                    binding.btnSort.text = getString(R.string.review_likes_order)
            }

            loadReviewList()
        }
    }

    private fun loadReviewList() {

        val filterData = mainViewModel.filterData.value
        var writerFilter = FILTER_ALL
        var tagFilter = listOf(1, 2, 3, 4, 5)

        // null check
        if (filterData != null) {
            writerFilter = filterData.writerFilter
            tagFilter = filterData.tagFilter

            // tag 비어있으면 모두 선택
            if (tagFilter.isEmpty())
                tagFilter = listOf(1, 2, 3, 4, 5)
        }

        // review list 갱신
        val request = RequestReviewListData(mainViewModel.selectedMajor.value!!.majorId, writerFilter, tagFilter)

        // 정렬
        var sort = "recent"
        if (reviewListViewModel.dropDownSelected.value != null) {
            sort = if (reviewListViewModel.dropDownSelected.value!!.id == 1)
                "recent"
            else
                "like"
        }
        reviewListViewModel.getReviewList(sort, request)

    }

    private fun initSortSelected() {
        reviewListViewModel.dropDownSelected.value = SelectableData(1, getString(R.string.review_latest_order), true)
    }


    private fun initBottomSheet() {

        majorBottomSheetDialog = CustomBottomSheetDialog(resources.getString(R.string.bottom_sheet_title_major))
        filterBottomSheetDialog = FilterBottomSheetDialog()

        observeBottomSheet(mainViewModel, majorBottomSheetDialog)
        majorBottomSheetDialog.setCompleteListener {
            val selectedData = majorBottomSheetDialog.getSelectedData()
            if (selectedData != null) {
                val majorData = MajorData(selectedData.id, selectedData.name)
                mainViewModel.setSelectedMajor(majorData)
            }

        }

        setFilterApplyListener()

    }

}