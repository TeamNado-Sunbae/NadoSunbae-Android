package com.nadosunbae_android.data.datasource.remote.main

import com.nadosunbae_android.data.model.response.mypage.ResponseAppLinkData

interface MainDataSource {


    suspend fun getAppLink(): ResponseAppLinkData
}