package com.nadosunbae_android.api.review

import com.nadosunbae_android.model.request.review.RequestPostReview
import com.nadosunbae_android.model.request.review.RequestReviewListData
import com.nadosunbae_android.model.response.review.*
import retrofit2.Call
import retrofit2.http.*

interface ReviewService {

    @POST("review-post/list")
    suspend fun getReviewList(
        @Query("sort") sort: String = "recent",
        @Body requestBody: RequestReviewListData
    ) : ResponseReviewListData

    @GET("major/{majorId}")
    suspend fun getMajorInfo(
        @Path("majorId") majorId: Int
    ) : ResponseMajorData

    @GET("review-post/{postId}")
    suspend fun getReviewDetail(
        @Path("postId") postId: Int
    ) : ResponseReviewDetailData

    @GET("review-post/background-image/list")
    suspend fun getBackgroundImageList() : ResponseBackgroundImageListData

    @POST("review-post")
    suspend fun postReview(
        @Body requestBody: RequestPostReview
    ) : ResponsePostReview

}