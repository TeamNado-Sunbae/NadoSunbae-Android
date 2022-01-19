package com.nadosunbae_android.data.repository.main

import com.nadosunbae_android.data.model.response.sign.ResponseMajorData
import retrofit2.Response

interface MainRepository {

    fun getMajorList(universityId: Int, filter: String = "all",
        onResponse: (Response<ResponseMajorData>) -> Unit,
        onFailure: (Throwable) -> Unit
        )
}