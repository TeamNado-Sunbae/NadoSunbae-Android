package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.ui.PreviewData
import com.nadosunbae_android.databinding.FragmentClassRoomReviewBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewListAdapter


class ClassRoomReviewFragment : BaseFragment<FragmentClassRoomReviewBinding>(R.layout.fragment_class_room_review) {
    private lateinit var reviewListAdapter : ReviewListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClassRoomReview()
    }




    private fun initClassRoomReview(){
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
        reviewListAdapter = ReviewListAdapter()
        binding.rcClassroomReview.adapter = reviewListAdapter
        reviewListAdapter.setReviewListData(previewData)
    }
}