package com.nadosunbae_android.app.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import com.nadosunbae_android.databinding.FragmentClassRoomReviewBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.review.adapter.ReviewListAdapter
import java.text.SimpleDateFormat


class ClassRoomReviewFragment : BaseFragment<FragmentClassRoomReviewBinding>(R.layout.fragment_class_room_review) {
    private lateinit var reviewListAdapter : ReviewListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClassRoomReview()
    }




    private fun initClassRoomReview(){
        val from = "2022-01-18 10:10:10"
        val transFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sampleData: ResponseReviewListData.Data = ResponseReviewListData.Data(
               transFormat.parse(from),
            "2", "한줄평", 12, listOf(
                ResponseReviewListData.Data.Tag("월 배우나요?"),
                ResponseReviewListData.Data.Tag("향후 진로")
            ), ResponseReviewListData.Data.Writer("컴퓨터공학", "18-1", "홍길동", 13, "물리학", "19-1", 22)

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