package com.nadosunbae_android.data.api.app

import com.nadosunbae_android.data.model.response.app.ResponseAppBanner
import com.nadosunbae_android.data.model.response.app.ResponseAppVersion
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {
    //홈 배너 가져오기
    @GET("app/banner")
    suspend fun getAppBanner(
        @Query("type") type : String
    ) : ResponseAppBanner

    //앱 최신 버전 체크
    @GET("app/version/recent")
    suspend fun getAppVersion() : ResponseAppVersion
}