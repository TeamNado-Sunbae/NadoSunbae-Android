package com.nadosunbae_android.data.datasource.remote.main

import com.nadosunbae_android.data.api.main.MainService
import com.nadosunbae_android.data.model.response.major.ResponseMajorListData
import com.nadosunbae_android.data.model.response.mypage.ResponseAppLinkData

class MainDataSourceImpl(private val service: MainService) : MainDataSource {

    override suspend fun getMajorList(universityId: Int, filter: String): ResponseMajorListData {
        return service.getMajorList(universityId, filter)
    }

    override suspend fun getAppLink(): ResponseAppLinkData {
        return service.getAppLink()
    }
}