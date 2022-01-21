package com.nadosunbae_android.data.repository.sign

import com.nadosunbae_android.data.model.request.sign.RequestSignEmail
import com.nadosunbae_android.data.model.request.sign.RequestSignIn
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.data.model.request.sign.RequestSignUp
import com.nadosunbae_android.data.model.response.sign.*
import retrofit2.Response

interface SignRepository {
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

    fun postSignIn(
        requestSignIn: RequestSignIn,
        onResponse: (Response<ResponseSignIn>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}