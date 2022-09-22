package com.nadosunbae_android.data.datasource.remote.app

import com.nadosunbae_android.data.model.response.app.ResponseAppBanner

interface AppDataSource {
    suspend fun getAppBanner(type : String) : ResponseAppBanner
}