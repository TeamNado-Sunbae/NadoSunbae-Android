package com.nadosunbae_android.datasource.remote.main

import com.nadosunbae_android.model.response.main.ResponseMajorListData
import retrofit2.Response

interface MainDataSource {

    suspend fun getMajorList(
        universityId: Int,
        filter: String = "all"
    ) : ResponseMajorListData
}