package com.nadosunbae_andorid.presentation.ui.review

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.FragmentReviewBinding
import com.nadosunbae_andorid.presentation.base.BaseFragment


class ReviewFragment : BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStickyHeader()
        setPreviewData()
    }

    private fun setPreviewData() {
        
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

}