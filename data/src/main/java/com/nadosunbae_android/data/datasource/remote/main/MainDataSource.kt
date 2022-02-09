package com.nadosunbae_android.data.datasource.remote.main

import com.nadosunbae_android.domain.model.response.major.ResponseMajorListData

interface MainDataSource {

    suspend fun getMajorList(
        universityId: Int,
        filter: String = "all"
    ) : ResponseMajorListData
}