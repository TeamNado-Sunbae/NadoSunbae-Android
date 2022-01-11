package com.nadosunbae_andorid.presentation.ui.review

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.FragmentReviewBinding
import com.nadosunbae_andorid.presentation.base.BaseFragment
import com.nadosunbae_andorid.presentation.ui.review.viewmodel.ReviewListViewModel
import kotlinx.android.synthetic.main.fragment_review.*


class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

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
        setPreviewData()
        setClickListener()
        observePreviewList()
    }

    private fun setBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = reviewListViewModel
    }

    private fun setPreviewData() {

    }

    private fun setClickListener() {
        binding.btnMajorPage.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(reviewListViewModel.urlHomepage.value))
            startActivity(intent)
        }

        binding.btnSubjectTable.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(reviewListViewModel.urlSubjectTable.value))
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

}