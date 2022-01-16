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
import com.bumptech.glide.Glide
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.review.PreviewData
import com.nadosunbae_android.databinding.FragmentReviewBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewListAdapter
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewListViewModel

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
        setPreviewData()
        setClickListener()
        observePreviewList()

        setTestData()
    }

    private fun setBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel
        binding.reviewListViewModel = reviewListViewModel
    }

    private fun setPreviewData() {

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

        val sampleData = PreviewData(
            "22.01.12",
            4,
            "한줄평입니다",
            listOf("뭘배우나요?"),
            "닉네임",
            "18-1 본전공",
            "20-1 제2전공"
        )
        val previewData = mutableListOf(
            sampleData,
            sampleData,
            sampleData,
            sampleData,
            sampleData
        )

            reviewListAdapter.setReviewListData(previewData)
    }

}