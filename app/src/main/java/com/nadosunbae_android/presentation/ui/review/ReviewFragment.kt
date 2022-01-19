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
import com.nadosunbae_android.data.model.response.sign.BottomSheetData
import com.nadosunbae_android.databinding.FragmentReviewBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewListAdapter
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewListViewModel
import com.nadosunbae_android.presentation.ui.sign.CustomBottomSheetDialog

class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private lateinit var reviewListAdapter : ReviewListAdapter

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
        initReviewListData()
        setClickListener()
        observePreviewList()

        setTestData()
    }

    private fun setBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel
        binding.reviewListViewModel = reviewListViewModel
    }

    private fun initReviewListData() {
        reviewListViewModel.getReviewList(
            "recent", RequestReviewListData(5, 1, listOf(1, 2, 3, 4, 5))
        )
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
                    // reviewId 값을 intent로 넘겨줄 것!!
                    var intent = Intent(context, ReviewDetailActivity::class.java)
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
            var intent = Intent(context, ReviewWriteActivity::class.java)
            startActivity(intent)
        }

        binding.btnSelectMajor.setOnClickListener {
            val bottomSheetDialog = CustomBottomSheetDialog()
            bottomSheetDialog.show(parentFragmentManager, bottomSheetDialog.tag)


            // test data
            var majorSelectionData = mutableListOf(
                BottomSheetData(1,"xxx학과", false),
                BottomSheetData(2,"aaa학과", false),
                BottomSheetData(3,"bbb학과", false),
                BottomSheetData(4,"ccc학", false),
                BottomSheetData(5,"ddd학과", false),
                BottomSheetData(6,"eeee학과", false),
                BottomSheetData(7,"iow학과", false),
                BottomSheetData(8,"컴퓨터공학", false),
                BottomSheetData(9,"18-1", false),
                BottomSheetData(10,"17-2", false),
                BottomSheetData(11,"17-1", false),
                BottomSheetData(12,"16-2", false),
                BottomSheetData(13,"16-1", false),
                BottomSheetData(14,"15-2", false),
                BottomSheetData(15,"15-1", false),
                BottomSheetData(16,"15년 이전", false),
            )
            bottomSheetDialog.setDataList(majorSelectionData)
        }

        binding.btnReviewFilter.setOnClickListener {
            val filterBottomSheetData = FilterBottomSheetDialog()
            filterBottomSheetData.show(parentFragmentManager, filterBottomSheetData.tag)
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

    private fun setTestData() {
        reviewListViewModel.setGraphicUrl("https://user-images.githubusercontent.com/37872134/149070363-b5fa6564-3c83-4285-b426-13e6095b8016.png")
        reviewListViewModel.setPageUrl("https://www.naver.com")
        reviewListViewModel.setSubjectTableUrl("https://www.daum.net")
        mainViewModel.setSelectedMajor("국어국문학과")
    }

}