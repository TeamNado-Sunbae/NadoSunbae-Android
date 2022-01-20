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
import com.nadosunbae_android.data.model.ui.MajorData
import com.nadosunbae_android.databinding.FragmentReviewBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewListAdapter
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewListViewModel
import com.nadosunbae_android.util.CustomBottomSheetDialog

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
        observeSelectedMajor()
        observePreviewList()
        initBottomSheet()
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
                    // postId Intent로 전달
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
            val intent = Intent(context, ReviewWriteActivity::class.java)

            val selectedMajor = mainViewModel.selectedMajor.value
            val firstMajor = mainViewModel.firstMajor.value
            val secondMajor = mainViewModel.secondMajor.value
            // null check
            if (selectedMajor != null)
                intent.putExtra("selectedMajor", selectedMajor)
            if (firstMajor != null)
                intent.putExtra("firstMajor", firstMajor)
            if (secondMajor != null)
                intent.putExtra("secondMajor", secondMajor)

            startActivity(intent)
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

                // review list 갱신
                val request = RequestReviewListData(mainViewModel.selectedMajor.value!!.majorId, 1, listOf(1, 2, 3, 4, 5))
                reviewListViewModel.getReviewList("recent", request)

                // 선택된 학과 정보 불러오기
                reviewListViewModel.getMajorInfo(mainViewModel.selectedMajor.value!!.majorId)
            }
        }
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

    }

}