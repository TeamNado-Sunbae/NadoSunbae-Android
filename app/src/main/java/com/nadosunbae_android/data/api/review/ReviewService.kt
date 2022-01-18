package com.nadosunbae_android.data.api.review

import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import retrofit2.Call
import retrofit2.http.*

interface ReviewService {

    @POST("review-post/list")
    fun getReviewList(
        @Query("sort") sort: String = "recent",
        @Body requestBody: RequestReviewListData
    ) : Call<ResponseReviewListData>
}