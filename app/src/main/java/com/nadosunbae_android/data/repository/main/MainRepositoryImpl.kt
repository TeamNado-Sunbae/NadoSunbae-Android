package com.nadosunbae_android.data.repository.main

import com.nadosunbae_android.data.datasource.remote.main.MainDataSource
import com.nadosunbae_android.data.datasource.remote.main.MainDataSourceImpl
import com.nadosunbae_android.data.model.response.sign.ResponseMajorData
import retrofit2.Response

class MainRepositoryImpl : MainRepository {
    val mainDataSource: MainDataSource = MainDataSourceImpl()

    override fun getMajorList(
        universityId: Int,
        filter: String,
        onResponse: (Response<ResponseMajorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return mainDataSource.getMajorList(universityId, filter, onResponse, onFailure)
    }
}