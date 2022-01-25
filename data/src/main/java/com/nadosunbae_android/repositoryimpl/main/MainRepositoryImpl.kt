package com.nadosunbae_android.repositoryimpl.main

import com.nadosunbae_android.datasource.remote.main.MainDataSource
import com.nadosunbae_android.datasource.remote.main.MainDataSourceImpl
import com.nadosunbae_android.model.response.main.ResponseMajorListData
import retrofit2.Response

class MainRepositoryImpl : MainRepository {
    val mainDataSource: MainDataSource = MainDataSourceImpl()

    override fun getMajorList(
        universityId: Int,
        filter: String,
        onResponse: (Response<ResponseMajorListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return mainDataSource.getMajorList(universityId, filter, onResponse, onFailure)
    }
}