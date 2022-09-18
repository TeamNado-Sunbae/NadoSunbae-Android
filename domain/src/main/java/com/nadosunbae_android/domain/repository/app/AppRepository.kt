package com.nadosunbae_android.domain.repository.app

import com.nadosunbae_android.domain.model.app.AppBannerData

interface AppRepository {
    suspend fun getAppBanner(type : String) : AppBannerData
}