package com.nadosunbae_android.data.datasource.remote.main

import com.nadosunbae_android.data.api.main.MainService
import com.nadosunbae_android.data.model.response.mypage.ResponseAppLinkData
import javax.inject.Inject

class MainDataSourceImpl @Inject constructor(private val service: MainService) : MainDataSource {

    override suspend fun getAppLink(): ResponseAppLinkData {
        return service.getAppLink()
    }
}