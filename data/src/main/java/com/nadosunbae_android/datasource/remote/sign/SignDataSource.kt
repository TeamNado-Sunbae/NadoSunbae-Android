package com.nadosunbae_android.datasource.remote.sign

import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*
import retrofit2.Response

interface SignDataSource {
    fun postSignNickname(
        requestSignNickname: RequestSignNickname,
        onResponse: (Response<ResponseSignNickname>) -> Unit,
        onFailure: (Throwable) -> Unit)

    fun postSignEmail(
        requestSignEmail: RequestSignEmail,
        onResponse: (Response<ResponseSignEmail>) -> Unit,
        onFailure: (Throwable) -> Unit)

    fun getFirstDepartment(
        universityId : Int,
        filter : String,
        onResponse: (Response<ResponseFirstDepartment>) -> Unit,
        onFailure: (Throwable) -> Unit)

    fun postSignUp(
        requestSignUp: RequestSignUp,
        onResponse: (Response<ResponseSignUp>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    //로긍인
    fun postSignIn(
        requestSignIn: RequestSignIn,
        onResponse: (Response<ResponseSignIn>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}
