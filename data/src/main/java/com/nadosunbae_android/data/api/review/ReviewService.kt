package com.nadosunbae_android.data.api.review

import com.nadosunbae_android.domain.model.request.review.RequestPutReviewData
import com.nadosunbae_android.domain.model.response.review.ResponseDeleteReview
import com.nadosunbae_android.domain.model.response.review.ResponsePutReviewData
import com.nadosunbae_android.domain.model.request.review.RequestPostReviewData
import com.nadosunbae_android.domain.model.request.review.RequestReviewListData
import com.nadosunbae_android.domain.model.response.review.*
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

    @DELETE("review-post/{postId}")
    fun deleteReview(
        @Path("postId") postId: Int
    ) : ResponseDeleteReview

    @PUT("review-post/{postId}")
    fun putReview(
        @Path("postId") postId: Int,
        @Body requestBody: RequestPutReviewData
    ) : ResponsePutReviewData

    @GET("review-post/background-image/list")
    suspend fun getBackgroundImageList() : ResponseBackgroundImageListData

    @POST("review-post")
    suspend fun postReview(
        @Body requestBody: RequestPostReviewData
    ) : ResponsePostReviewData

}