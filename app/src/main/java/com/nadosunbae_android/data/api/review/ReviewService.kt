package com.nadosunbae_android.data.api.review

import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ReviewService {

    @GET("/review-post/list")
    fun getReviewList(
        @Header("accesstoken") token: String,
        @Query("sort") sort: String = "recent",
        @Body requestBody: RequestReviewListData
    ) : Call<ResponseReviewListData>
}