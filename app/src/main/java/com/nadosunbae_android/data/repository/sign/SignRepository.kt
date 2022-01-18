package com.nadosunbae_android.data.repository.sign

import com.nadosunbae_android.data.model.response.sign.ResponseSignNickname
import retrofit2.Response

interface SignRepository {
    fun postSignNickname(
        requestSignNickname: String,
        onResponse: (Response<ResponseSignNickname>) -> Unit,
        onFailure: (Throwable) -> Unit)
}