package com.nadosunbae_android.data.api.mypage

import com.nadosunbae_android.data.model.request.mypage.RequestMyPageBlockUpdate
import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.request.mypage.RequestQuit
import com.nadosunbae_android.data.model.request.mypage.RequestResetPassword
import com.nadosunbae_android.data.model.response.mypage.*
import com.nadosunbae_android.data.model.response.user.ResponseUserInfo
import retrofit2.http.*

interface MyPageService {
    //마이페이지 메인 내 정보
    @GET("user/mypage/{userId}")
    suspend fun getMyPageMyInfo(
        @Path("userId") userId: Int
    ) : ResponseUserInfo

    //내 정보 수정
    @PUT("user")
    suspend fun putMyPageModify(
        @Body requestMyPageModify: RequestMyPageModify
    ) : ResponseMyPageModify

    //버전 정보
    @GET("user/mypage/app-version/recent")
    suspend fun getMyPageVersion(): ResponseMyPageVersionData

    //로그아웃
    @POST("auth/logout")
    suspend fun postMyPageLogOut(): ResponseMyPageLogOut

    //마이페이지 차단목록 조회
    @GET("block/list")
    suspend fun getMyPageBlock() :ResponseMyPageBlock

    //마이페이지 유저 차단 & 차단 해제
    @POST("block")
    suspend fun postBlockUpdate(
        @Body requestMyPageBlockUpdate: RequestMyPageBlockUpdate
    ): ResponseMyPageBlockUpdate

    //비밀번호 재설정
    @POST("auth/reset/password")
    suspend fun postResetPassword(
        @Body requestResetPassword: RequestResetPassword
    ) : ResponseResetPassword

    @POST("auth/secession")
    suspend fun deleteMyPageQuit(
        @Body requestQuit: RequestQuit
    ) : ResponseQuitData
}