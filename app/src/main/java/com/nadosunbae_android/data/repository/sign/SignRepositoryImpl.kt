package com.nadosunbae_android.data.repository.sign

import com.nadosunbae_android.data.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.data.datasource.remote.sign.SignDataSourceImpl
import com.nadosunbae_android.data.model.request.sign.RequestSignEmail
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.data.model.response.sign.ResponseFirstDepartment
import com.nadosunbae_android.data.model.response.sign.ResponseSignEmail
import com.nadosunbae_android.data.model.response.sign.ResponseSignNickname
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
}