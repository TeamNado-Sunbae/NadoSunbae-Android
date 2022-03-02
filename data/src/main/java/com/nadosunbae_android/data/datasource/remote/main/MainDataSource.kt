package com.nadosunbae_android.data.datasource.remote.main

import com.nadosunbae_android.data.model.response.major.ResponseMajorListData
import com.nadosunbae_android.data.model.response.mypage.ResponseAppLinkData

interface MainDataSource {

    suspend fun getMajorList(
        universityId: Int,
        filter: String = "all"
    ) : ResponseMajorListData

    suspend fun getAppLink() : ResponseAppLinkData
}