package com.nadosunbae_android.data.datasource.remote.home

import com.nadosunbae_android.data.api.home.HomeService
import com.nadosunbae_android.data.model.response.home.ResponseHomeRanking
import com.nadosunbae_android.data.model.response.home.ResponseUnivReview
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(private val service: HomeService) : HomeDataSource {
    override suspend fun getUnviReview(university: Int): ResponseUnivReview {
        return service.getUnivReview(university)
    }

    override suspend fun getHomeRanking(university: Int): ResponseHomeRanking {
        return service.getHomeRanking(university)
    }

}