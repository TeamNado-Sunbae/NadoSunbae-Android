package com.nadosunbae_android.datasource.remote.sign

import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*
import retrofit2.Response

interface SignDataSource {
    suspend fun postSignNickname(
        requestSignNickname: RequestSignNickname) : ResponseSignNickname

    suspend fun postSignEmail(
        requestSignEmail: RequestSignEmail) : ResponseSignEmail

    suspend fun getFirstDepartment(
        universityId : Int,
        filter : String) : ResponseFirstDepartment

    suspend fun postSignUp(requestSignUp: RequestSignUp) : ResponseSignUp

    //로긍인
    suspend fun postSignIn(requestSignIn: RequestSignIn) : ResponseSignIn
}
