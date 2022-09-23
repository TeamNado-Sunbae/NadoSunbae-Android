package com.nadosunbae_android.data.datasource.remote.app

import com.nadosunbae_android.data.api.app.AppService
import com.nadosunbae_android.data.model.response.app.ResponseAppBanner
import com.nadosunbae_android.data.model.response.app.ResponseAppVersion
import javax.inject.Inject

class AppDataSourceImpl @Inject constructor(private val service: AppService) : AppDataSource {
    override suspend fun getAppBanner(type: String): ResponseAppBanner {
        return service.getAppBanner(type)
    }

    override suspend fun getAppVersion(): ResponseAppVersion {
        return service.getAppVersion()
    }
}