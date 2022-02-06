package com.nadosunbae_android.api

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
    ): Call<ResponseSignNickname>

    @POST("auth/duplication-check/email")
    suspend fun postSignEmail(
        @Body requestSignEmail: RequestSignEmail
    ) : Call<ResponseSignEmail>

    @GET("major/list/{universityId}")
    suspend fun getFirstDepartment(
        @Path("universityId") universityId :Int,
        @Query("filter") filter : String
    ) : Call<ResponseFirstDepartment>

    @POST("auth/signup")
    suspend fun postSignUp(
        @Body requestSignUp: RequestSignUp
    ) : Call<ResponseSignUp>

    //로그인
    @POST("auth/login")
    suspend fun postSignIn(
        @Body requestSignIn: RequestSignIn
    ) : Call<ResponseSignIn>
}