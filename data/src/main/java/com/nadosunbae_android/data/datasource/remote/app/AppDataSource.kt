package com.nadosunbae_android.data.datasource.remote.app

import com.nadosunbae_android.data.model.response.app.ResponseAppBanner
import com.nadosunbae_android.data.model.response.app.ResponseAppVersion

interface AppDataSource {
    suspend fun getAppBanner(type : String) : ResponseAppBanner

    //앱 버전
    suspend fun getAppVersion() : ResponseAppVersion
}