package com.nadosunbae_android.repositoryimpl.main

import com.nadosunbae_android.model.response.main.ResponseMajorListData
import retrofit2.Response

interface MainRepository {

    fun getMajorList(universityId: Int, filter: String = "all",
        onResponse: (Response<ResponseMajorListData>) -> Unit,
        onFailure: (Throwable) -> Unit
        )
}