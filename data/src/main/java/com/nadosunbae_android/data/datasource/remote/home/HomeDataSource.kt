package com.nadosunbae_android.data.datasource.remote.home

import com.nadosunbae_android.data.model.response.home.ResponseUnivReview

interface HomeDataSource {
    suspend fun getUnviReview(university: Int) : ResponseUnivReview
}