package com.nadosunbae_android.app.presentation.ui.home

import androidx.lifecycle.ViewModel
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
}