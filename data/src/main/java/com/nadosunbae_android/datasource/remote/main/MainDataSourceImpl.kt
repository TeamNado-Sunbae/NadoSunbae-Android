package com.nadosunbae_android.datasource.remote.main

import com.nadosunbae_android.api.main.MainService
import com.nadosunbae_android.model.response.main.ResponseMajorListData

class MainDataSourceImpl(private val service : MainService) : MainDataSource {

    override suspend fun getMajorList(universityId: Int, filter: String): ResponseMajorListData {
        return service.getMajorList(universityId, filter)
    }
}