package com.nadosunbae_android.api.mypage

import com.nadosunbae_android.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyPageService {
    @GET("user/mypage/{userId}/classroom-post/list")
    fun getMyPageQuestion(
        @Path("userId") userId :Int,
        @Query("sort") sort : String
    ) : Call<ResponseMypageQuestionData>

    @GET("user/mypage")
    fun getMyPageMyInfo() : Call<ResponseMypageMyInfo>
}