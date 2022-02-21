package com.nadosunbae_android.data.api.mypage

import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.response.mypage.*
import retrofit2.http.*

interface MyPageService {
    //마이페이지 메인 나에게 온 1:1 질문
    @GET("user/mypage/{userId}/classroom-post/list")
    suspend fun getMyPageQuestion(
        @Path("userId") userId: Int,
        @Query("sort") sort: String = "recent"
    ) : ResponseMypageQuestionData

    //마이페이지 메인 내 정보
    @GET("user/mypage/{userId}")
    suspend fun getMyPageMyInfo(
        @Path("userId") userId: Int
    ) : ResponseMypageMyInfo

    //내 정보 수정
    @PUT("user/mypage")
    suspend fun putMyPageModify(
        @Body requestMyPageModify: RequestMyPageModify
    ) : ResponseMyPageModify

    //내가 쓴 글
    @GET("user/mypage/classroom-post/list")
    suspend fun getPostByMe(
        @Query("type") type: String = "question"
    ) : ResponseMyPagePostData

    //내가 쓴 답글
    @GET("user/mypage/comment/list/{postTypeId}")
    suspend fun getMyPageReply(
        @Path("postTypeId") postTypeId: Int
    ) : ResponseMyPageReplyData

    //버전 정보
    @GET("user/mypage/app-version/recent")
    suspend fun getMyPageVersion(): ResponseMyPageVersionData

    //로그아웃
    @POST("auth/logout")
    suspend fun postMyPageLogOut(): ResponseMyPageLogOut
}