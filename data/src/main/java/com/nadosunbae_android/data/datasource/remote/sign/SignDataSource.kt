package com.nadosunbae_android.data.datasource.remote.sign

import com.nadosunbae_android.data.model.request.sign.*
import com.nadosunbae_android.data.model.response.sign.*

interface SignDataSource {
    //닉네임 중복확인
    suspend fun postSignNickname(requestSignNickname: RequestSignNickname) : ResponseSignNickname

    //이메일 중복확인
    suspend fun postSignEmail(requestSignEmail: RequestSignEmail) : ResponseSignEmail

    //회원가입
    suspend fun postSignUp(requestSignUp: RequestSignUp) : ResponseSignUp

    //로그인
    suspend fun postSignIn(requestSignIn: RequestSignIn) : ResponseSignIn

    //이메일 재전송
    suspend fun postCertificationEmail(requestCertificationEmail: RequestCertificationEmail): ResponseCertificationEmail

    //토큰 재발급 및 자동 로그인
    suspend fun postRenewalToken(): ResponseSignIn

    //학교 이메일 조회
    suspend fun getUnivEmail(universityId : Int) : ResponseUnivEmail
}
