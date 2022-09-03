package com.nadosunbae_android.data.datasource.remote.home

import com.nadosunbae_android.data.api.home.HomeService
import com.nadosunbae_android.data.model.response.home.ResponseUnivReview

class HomeDataSourceImpl(private val service: HomeService) : HomeDataSource {
    override suspend fun getUnviReview(university: Int): ResponseUnivReview {
        return service.getUnivReview(university)
    }

}