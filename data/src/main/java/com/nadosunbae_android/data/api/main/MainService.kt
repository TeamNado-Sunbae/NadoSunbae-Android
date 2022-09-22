package com.nadosunbae_android.data.api.main

import com.nadosunbae_android.data.model.response.mypage.ResponseAppLinkData
import retrofit2.http.GET

interface MainService {

    //각종 링크 조회
    @GET("app/link")
    suspend fun getAppLink() : ResponseAppLinkData

}