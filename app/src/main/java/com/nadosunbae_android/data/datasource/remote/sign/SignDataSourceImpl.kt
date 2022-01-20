package com.nadosunbae_android.data.datasource.remote.sign

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.model.request.sign.RequestSignEmail
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.data.model.request.sign.RequestSignUp
import com.nadosunbae_android.data.model.response.sign.ResponseFirstDepartment
import com.nadosunbae_android.data.model.response.sign.ResponseSignEmail
import com.nadosunbae_android.data.model.response.sign.ResponseSignNickname
import com.nadosunbae_android.data.model.response.sign.ResponseSignUp
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

    override fun postSignEmail(
        requestSignEmail: RequestSignEmail,
        onResponse: (Response<ResponseSignEmail>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.signService.postSignEmail(requestSignEmail).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun getFirstDepartment(
        universityId: Int,
        filter: String,
        onResponse: (Response<ResponseFirstDepartment>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.signService.getFirstDepartment(universityId, filter).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun postSignUp(
        requestSignUp: RequestSignUp,
        onResponse: (Response<ResponseSignUp>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.signService.postSignUp(requestSignUp).enqueueUtil(
            onResponse, onFailure
        )
    }

}