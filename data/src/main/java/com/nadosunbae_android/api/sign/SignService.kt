package com.nadosunbae_android.api.sign

import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*
import retrofit2.Call
import retrofit2.http.*

interface SignService {
    @POST("auth/duplication-check/nickname")
    suspend fun postSignNickname(
        @Body requestSignNickname: RequestSignNickname
    ): ResponseSignNickname

    @POST("auth/duplication-check/email")
    suspend fun postSignEmail(
        @Body requestSignEmail: RequestSignEmail
    ) : ResponseSignEmail

    @GET("major/list/{universityId}")
    suspend fun getFirstDepartment(
        @Path("universityId") universityId :Int,
        @Query("filter") filter : String
    ) : ResponseFirstDepartment

    @POST("auth/signup")
    suspend fun postSignUp(
        @Body requestSignUp: RequestSignUp
    ) : ResponseSignUp

    //로그인
    @POST("auth/login")
    suspend fun postSignIn(
        @Body requestSignIn: RequestSignIn
    ) : ResponseSignIn
}