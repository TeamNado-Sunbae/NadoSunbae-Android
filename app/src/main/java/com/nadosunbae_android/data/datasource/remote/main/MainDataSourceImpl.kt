package com.nadosunbae_android.data.datasource.remote.main

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.model.response.sign.ResponseMajorData
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class MainDataSourceImpl() : MainDataSource {

    override fun getMajorList(
        universityId: Int,
        filter: String,
        onResponse: (Response<ResponseMajorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.mainService.getMajorList(universityId, filter).enqueueUtil(
            onResponse, onFailure
        )
    }

}