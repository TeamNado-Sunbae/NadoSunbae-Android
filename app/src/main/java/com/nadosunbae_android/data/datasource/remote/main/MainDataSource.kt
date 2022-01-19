package com.nadosunbae_android.data.datasource.remote.main

import com.nadosunbae_android.data.model.response.main.ResponseMajorListData
import retrofit2.Response

interface MainDataSource {

    fun getMajorList(
        universityId: Int,
        filter: String = "all",
        onResponse: (Response<ResponseMajorListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}