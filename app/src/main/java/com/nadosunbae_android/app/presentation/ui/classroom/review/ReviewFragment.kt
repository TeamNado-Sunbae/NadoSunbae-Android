package com.nadosunbae_android.app.presentation.ui.classroom.review

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentReviewBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel.Companion.FILTER_ALL
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewWriteActivity.Companion.MODE_NEW
import com.nadosunbae_android.app.presentation.ui.classroom.review.adapter.ReviewListAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.review.viewmodel.ReviewListViewModel
import com.nadosunbae_android.app.util.*
import com.nadosunbae_android.domain.model.main.MajorSelectData
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.review.ReviewFilterItem
import com.nadosunbae_android.domain.model.review.ReviewPreviewData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    // main vm
    private val mainViewModel: MainViewModel by activityViewModels()

    // reviewList vm
    private val reviewListViewModel: ReviewListViewModel by viewModels({requireParentFragment().requireParentFragment()})
    private lateinit var reviewListAdapter : ReviewListAdapter

    private lateinit var majorBottomSheetDialog: CustomBottomSheetDialog
    private lateinit var filterBottomSheetDialog: FilterBottomSheetDialog

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // 마이페이지로 이동하도록 콜백 받음
        if (it.resultCode == GOTO_MYPAGE) {
            mainViewModel.bottomNavItem.value = MainActivity.MYPAGE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBinding()
//        setStickyHeader()
//        setScrollMinHeight()
        initReviewListAdapter()
        observeReviewListData()
        setClickListener()
//        initSortSelected()
//        observeSelectedMajor()
//        observeFilter()
//        observeSort()
//        initBottomSheet()
        observeLoadingEnd()
//        submitAnalytics()
    }

    override fun onResume() {
        super.onResume()
//        observeUserMajor()
//        updateMajorStatus()
        loadReviewList()
    }

    private fun setBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel
    }

    private fun observeReviewListData() {
        // reviewListViewModel observe (목록에 표시되도록)
        reviewListViewModel.reviewListData.observe(viewLifecycleOwner) {
            reviewListAdapter.setReviewListData(it as MutableList<ReviewPreviewData>)

            // empty review list -> 표시
            if (reviewListAdapter.isEmpty())
                binding.tvEmptyReview.visibility = View.VISIBLE
            else
                binding.tvEmptyReview.visibility = View.INVISIBLE
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
                    Timber.d("후기 메인, ${MainGlobals.signInData!!.isReviewInappropriate}")
                    CustomDialog(requireActivity()).restrictDialog(
                        requireActivity(),
                        ReviewGlobals.isReviewed,
                        MainGlobals.signInData!!.isUserReported,
                        MainGlobals.signInData!!.isReviewInappropriate,
                        MainGlobals.signInData!!.message.toString(),
                        behavior = {
                            val reviewListData = reviewListViewModel.reviewListData.value
                            // null check
                            if (reviewListData != null) {
                                // postId Intent로 전달 (후기 상세보기 이동)
                                val postId = reviewListData[position].postId
                                val intent = Intent(context, ReviewDetailActivity::class.java).apply {
                                    putExtra("postId", postId)
                                    putExtra("userId", mainViewModel.userId.value)
                                    putExtra("appLink", mainViewModel.appLink.value?.data?.kakaoTalkChannel)
                                }
                                activityResultLauncher.launch(intent)
                            }
                        }
                    )
                }

            }
        )


        val showMajorBottomSheetDialog = {
            majorBottomSheetDialog.show(parentFragmentManager, majorBottomSheetDialog.tag)

            // (학과 선택) 기본 선택값 적용 (MainActivity setDefaultMajor에서 관리)
            majorBottomSheetDialog.setSelectedData(mainViewModel.selectedMajor.value!!.majorId)
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

    private fun observeSelectedMajor() {
        // 선택 학과 observe
        mainViewModel.selectedMajor.observe(viewLifecycleOwner) {

            // 필터 초기화
            mainViewModel.clearFilter()

            // null check
            if (mainViewModel.selectedMajor != null) {

                loadReviewList()

                showLoading() // 로딩시작
                // 선택된 학과 정보 불러오기
                reviewListViewModel.getMajorInfo(mainViewModel.selectedMajor.value!!.majorId)
                ReviewGlobals.selectedMajor = it
            }
        }
    }

    private fun observeUserMajor() {

        mainViewModel.firstMajor.observe(viewLifecycleOwner) {
            ReviewGlobals.firstMajor = it
        }

        mainViewModel.secondMajor.observe(viewLifecycleOwner) {
            ReviewGlobals.secondMajor = it
        }


    }

    private fun observeLoadingEnd() {
        reviewListViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            dismissLoading()
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

        // 정렬
        var sort = "recent"
        if (reviewListViewModel.dropDownSelected.value != null) {
            sort = if (reviewListViewModel.dropDownSelected.value!!.id == 1)
                "recent"
            else
                "like"
        }

        // review list 갱신
        val selectedMajorData = mainViewModel.selectedMajor.value
        if (selectedMajorData != null) {
            val request = ReviewFilterItem(selectedMajorData.majorId, writerFilter, tagFilter)

            showLoading() // 로딩 시작
            reviewListViewModel.getReviewList(request, sort)
        }

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
                val majorData = MajorSelectData(selectedData.id, selectedData.name)
                mainViewModel.setSelectedMajor(majorData)
            }

        }

        setFilterApplyListener()

    }

    private fun updateMajorStatus() {
        val firstMajor = ReviewGlobals.firstMajor
        val secondMajor = ReviewGlobals.secondMajor

        if (firstMajor != null && secondMajor != null) {
            mainViewModel.setFirstMajor(firstMajor)
            mainViewModel.setSecondMajor(secondMajor)
        }
    }

    private fun submitAnalytics() {
        FirebaseAnalyticsUtil.selectTab(FirebaseAnalyticsUtil.Tab.REVIEW)
    }

    companion object {
        const val GOTO_MYPAGE = 1001
    }
}