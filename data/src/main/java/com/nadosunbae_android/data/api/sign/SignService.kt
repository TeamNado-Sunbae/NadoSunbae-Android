package com.nadosunbae_android.data.api.sign

import com.nadosunbae_android.data.model.request.sign.*
import com.nadosunbae_android.data.model.response.sign.*
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

    @POST("auth/certification/email")
    suspend fun postCertificationEmail(
        @Body requestCertificationEmail: RequestCertificationEmail
    ) : ResponseCertificationEmail

    @POST("auth/renewal/token")
    suspend fun postRenewalToken(
        @Header("refreshtoken") refreshToken: String
    ) : ResponseSignIn

}