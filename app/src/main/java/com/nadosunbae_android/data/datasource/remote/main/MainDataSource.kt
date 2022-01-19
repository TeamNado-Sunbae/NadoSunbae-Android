package com.nadosunbae_android.data.datasource.remote.main

import com.nadosunbae_android.data.model.response.sign.ResponseMajorData
import retrofit2.Response

interface MainDataSource {

    fun getMajorList(
        universityId: Int,
        filter: String = "all",
        onResponse: (Response<ResponseMajorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}