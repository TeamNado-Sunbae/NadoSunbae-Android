package com.nadosunbae_android.data.api.mypage

import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.data.model.response.mypage.ResponseMyPageModify
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData
import retrofit2.http.*

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

    @PUT("user/mypage")
    suspend fun putMyPageModify(
        @Body requestMyPageModify: RequestMyPageModify
    ) : ResponseMyPageModify
}