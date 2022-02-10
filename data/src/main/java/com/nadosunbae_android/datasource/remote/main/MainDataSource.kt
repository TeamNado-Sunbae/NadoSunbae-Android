package com.nadosunbae_android.datasource.remote.main

import com.nadosunbae_android.model.response.major.ResponseMajorListData

interface MainDataSource {

    suspend fun getMajorList(
        universityId: Int,
        filter: String = "all"
    ) : ResponseMajorListData
}