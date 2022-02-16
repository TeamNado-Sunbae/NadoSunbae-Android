package com.nadosunbae_android.data.api.mypage

import com.nadosunbae_android.data.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyPageService {
    @GET("user/mypage/{userId}/classroom-post/list")
    suspend fun getMyPageQuestion(
        @Path("userId") userId: Int,
        @Query("sort") sort: String = "recent"
    ) : ResponseMypageQuestionData

    @GET("user/mypage/{userId}")
    suspend fun getMyPageMyInfo(
        @Path("userId") userId: Int
    ) : ResponseMypageMyInfo
}