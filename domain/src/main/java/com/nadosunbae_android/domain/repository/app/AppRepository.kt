package com.nadosunbae_android.domain.repository.app

import com.nadosunbae_android.domain.model.app.AppBannerData
import com.nadosunbae_android.domain.model.app.AppVersionData
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    //앱 버전
    fun getAppVersion() : Flow<AppVersionData>
    //홈 배너
    suspend fun getAppBanner(type : String) : List<AppBannerData>

}