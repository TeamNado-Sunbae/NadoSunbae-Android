package com.nadosunbae_android.data.api.home

import com.nadosunbae_android.data.model.response.home.ResponseUnivReview
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {
    //학교 후기 전체 최신순 조회
    @GET("review/university/{universityId}")
    suspend fun getUnivReview(
        @Path("universityId") universityId : Int
    ) : ResponseUnivReview
}