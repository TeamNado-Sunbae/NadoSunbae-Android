package com.nadosunbae_android.data.api.app

import com.nadosunbae_android.data.model.response.app.ResponseAppBanner
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {
    @GET("app/banner")
    suspend fun getAppBanner(
        @Query("type") type : String
    ) : ResponseAppBanner
}