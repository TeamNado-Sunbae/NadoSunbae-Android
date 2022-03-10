package com.nadosunbae_android.app.presentation.ui.review

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import com.nadosunbae_android.app.R
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.main.MajorSelectData
import com.nadosunbae_android.app.databinding.FragmentReviewBinding
import com.nadosunbae_android.app.di.NadoSunBaeApplication
import com.nadosunbae_android.domain.model.review.ReviewFilterItem
import com.nadosunbae_android.domain.model.review.ReviewPreviewData
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.WebViewActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel.Companion.FILTER_ALL
import com.nadosunbae_android.app.presentation.ui.review.ReviewWriteActivity.Companion.MODE_NEW
import com.nadosunbae_android.app.presentation.ui.review.adapter.ReviewListAdapter
import com.nadosunbae_android.app.presentation.ui.review.viewmodel.ReviewListViewModel
import com.nadosunbae_android.app.util.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    // main vm
    private val mainViewModel: MainViewModel by sharedViewModel()

    // reviewList vm
    private val reviewListViewModel: ReviewListViewModel by viewModel()

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
        setStickyHeader()
        setScrollMinHeight()
        initReviewListAdapter()
        observeReviewListData()
        setClickListener()
        initSortSelected()
        observeSelectedMajor()
        observeFilter()
        observeSort()
        initBottomSheet()
        observeLoadingEnd()

    }

    override fun onResume() {
        super.onResume()
        observeUserMajor()
        observeIsReviewed()
        updateMajorStatus()
        loadReviewList()
    }

    private fun setBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel
    }

    // NestedScrollView 내부의 recycler 뷰의 minHeight을 동적으로 설정합니다. (구조 상 layout에서 한계 있음)
    private fun setScrollMinHeight() {
        binding.clReviewMain.viewTreeObserver.addOnGlobalLayoutListener(    // viewTreeObserver를 등록하여 height을 동적으로 가져옴
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.clReviewMain.viewTreeObserver.removeOnGlobalLayoutListener(this)    // 한번만 실행되도록 layoutListener를 제거

                    binding.clScroll.minHeight = binding.run {
                        clReviewMain.height - clReviewToolBar.height
                    }
                }
            }
        )
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

                    // 후기 작성 여부 확인
                    if (!ReviewGlobals.isReviewed) {
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

            }
        )

        binding.btnMajorPage.setOnClickListener {

            val majorInfo = reviewListViewModel.majorInfo.value
            if (majorInfo != null) {
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("url", majorInfo.homepage)
                startActivity(intent)
            }
        }

        binding.btnSubjectTable.setOnClickListener {
            val majorInfo = reviewListViewModel.majorInfo.value
            if (majorInfo != null) {
                var intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("url", majorInfo.subjectTable)
                startActivity(intent)
            }
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


            showCustomDropDown(reviewListViewModel, binding.btnSort, 160f.dpToPx, null, -1 * 16f.dpToPx, null, true, reviewListViewModel.dropDownSelected.value!!.id, dropDownList)
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
        if(MainGlobals.signInData!!.isReviewInappropriate || MainGlobals.signInData!!.isUserReported){
            CustomDialog(requireActivity()).genericDialog(
                CustomDialog.DialogData(
                    MainGlobals.signInData?.message.toString(),
                    resources.getString(R.string.sign_in_question),
                    resources.getString(R.string.email_certification_close)
                ),
                complete = {
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.question_kakao)))
                    startActivity(intent)
                },
                cancel = {}
            )
        }else{
            val intent = Intent(context, ReviewWriteActivity::class.java)
            intent.putExtra("mode", MODE_NEW)
            startActivity(intent)
        }

    }

    private fun setStickyHeader() {
        binding.svReview.run {
            header = binding.clReviewFunctionBox
            stickListener = { _ ->
                Timber.d("LOGGER_TAG: stickListener")
            }
            freeListener = { _ ->
                Timber.d("LOGGER_TAG: freeListener")
            }
        }
    }

    private fun observeIsReviewed() {
        mainViewModel.signData.observe(viewLifecycleOwner) {
            ReviewGlobals.isReviewed = it.isReviewed
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

    private fun observeFilter() {

        mainViewModel.filterData.observe(viewLifecycleOwner) {
            val filter = mainViewModel.filterData.value

            // null check
            if (filter != null) {

                filterBottomSheetDialog.setFilter(filter)

                // 필터 활성화
                binding.btnReviewFilter.isSelected = !(filter.writerFilter == FILTER_ALL && (filter.tagFilter == listOf(1, 2, 3, 4, 5) || filter.tagFilter.isEmpty()))
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

    companion object {
        const val GOTO_MYPAGE = 1001
    }
}