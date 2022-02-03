package com.nadosunbae_android.repositoryimpl.sign

import com.nadosunbae_android.api.ApiService
import com.nadosunbae_android.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.datasource.remote.sign.SignDataSourceImpl
import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class SignRepositoryImpl : SignRepository {
    val signDataSource : SignDataSource = SignDataSourceImpl()

    override fun postSignNickname(
        requestSignNickname: RequestSignNickname,
        onResponse: (Response<ResponseSignNickname>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return signDataSource.postSignNickname(requestSignNickname, onResponse, onFailure)
    }

    override fun postSignEmail(
        requestSignEmail: RequestSignEmail,
        onResponse: (Response<ResponseSignEmail>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return signDataSource.postSignEmail(requestSignEmail, onResponse, onFailure)
    }

    override fun getFirstDepartment(
        universityId: Int,
        filter: String,
        onResponse: (Response<ResponseFirstDepartment>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return signDataSource.getFirstDepartment(universityId, filter, onResponse, onFailure)
    }

    override fun postSignUp(
        requestSignUp: RequestSignUp,
        onResponse: (Response<ResponseSignUp>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return signDataSource.postSignUp(requestSignUp, onResponse, onFailure)
    }

    override fun postSignIn(
        requestSignIn: RequestSignIn,
        onResponse: (Response<ResponseSignIn>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.signService.postSignIn(requestSignIn).enqueueUtil(
            onResponse, onFailure
        )
    }
}