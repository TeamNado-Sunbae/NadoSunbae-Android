package com.nadosunbae_android.app.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.nadosunbae_android.domain.model.home.HomeCommunityData
import com.nadosunbae_android.domain.model.home.HomeQuestionData
import com.nadosunbae_android.domain.model.home.HomeReviewData

class homeViewModel : ViewModel() {
    val reviewData = listOf<HomeReviewData>(
        HomeReviewData("test","test","test"),
        HomeReviewData("test","test","test"),
        HomeReviewData("test","test","test"),
        HomeReviewData("test","test","test"),
        HomeReviewData("test","test","test"),
        HomeReviewData("test","test","test"),
        HomeReviewData("test","test","test")
    )

    val questionData = listOf<HomeQuestionData>(
        HomeQuestionData("test", "test", "test"),
        HomeQuestionData("test", "test", "test"),
        HomeQuestionData("test", "test", "test"),
        HomeQuestionData("test", "test", "test"),
        HomeQuestionData("test", "test", "test"),
        HomeQuestionData("test", "test", "test")
    )

    val communityData = listOf<HomeCommunityData>(
        HomeCommunityData("Test", "Test", "Test", "Test", "Test", 1,1),
        HomeCommunityData("Test", "Test", "Test", "Test", "Test", 1,1),
        HomeCommunityData("Test", "Test", "Test", "Test", "Test", 1,1),
        HomeCommunityData("Test", "Test", "Test", "Test", "Test", 1,1),
        HomeCommunityData("Test", "Test", "Test", "Test", "Test", 1,1),
        HomeCommunityData("Test", "Test", "Test", "Test", "Test", 1,1)
    )
}