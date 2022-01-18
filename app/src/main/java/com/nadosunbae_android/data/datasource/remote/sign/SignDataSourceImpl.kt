package com.nadosunbae_android.data.datasource.remote.sign

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.data.model.response.sign.ResponseSignNickname
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class SignDataSourceImpl : SignDataSource {
    override fun postSignNickname(
        requestSignNickname: RequestSignNickname,
        onResponse: (Response<ResponseSignNickname>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.signService.postSignNickname(requestSignNickname).enqueueUtil(
            onResponse, onFailure
        )
    }
}