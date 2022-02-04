package com.nadosunbae_android.repository.sign

import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*
import retrofit2.Response

interface SignRepository {
    suspend fun postSignNickname(
        requestSignNickname: RequestSignNickname,
        onResponse: (Response<ResponseSignNickname>) -> Unit,
        onFailure: (Throwable) -> Unit)

    suspend fun postSignEmail(
        requestSignEmail: RequestSignEmail,
        onResponse: (Response<ResponseSignEmail>) -> Unit,
        onFailure: (Throwable) -> Unit)

    suspend fun getFirstDepartment(
        universityId : Int,
        filter : String,
        onResponse: (Response<ResponseFirstDepartment>) -> Unit,
        onFailure: (Throwable) -> Unit)

    suspend fun postSignUp(
        requestSignUp: RequestSignUp,
        onResponse: (Response<ResponseSignUp>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    suspend fun postSignIn(
        requestSignIn: RequestSignIn,
        onResponse: (Response<ResponseSignIn>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}