package com.nadosunbae_android.domain.repository.home

import com.nadosunbae_android.domain.model.home.HomeRankingData
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData

interface HomeRepository {
    suspend fun getUnivReview(university: Int) : List<HomeUnivReviewData>

    suspend fun getHomeRanking(university: Int) : List<HomeRankingData>
}