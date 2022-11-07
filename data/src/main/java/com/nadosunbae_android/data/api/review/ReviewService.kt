package com.nadosunbae_android.data.api.review

import com.nadosunbae_android.data.model.request.review.RequestPutReviewData
import com.nadosunbae_android.data.model.response.review.ResponseDeleteReview
import com.nadosunbae_android.data.model.response.review.ResponsePutReviewData
import com.nadosunbae_android.data.model.request.review.RequestPostReviewData
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.*
import retrofit2.http.*

interface ReviewService {

    @GET("review/major/{majorId}")
    suspend fun getReviewListByMajor(
        @Path("majorId") majorId: Int,
        @Query("sort") sort: String = "recent",
        @Query("tagFilter", encoded = true) tagFilter: String,
        @Query("writerFilter") writerFilter: String
    ) : ResponseReviewListData

    @GET("review/university/{univId}")
    suspend fun getReviewListByUniv(
        @Path("univId") univId: Int
    ) : ResponseReviewListData

    @GET("major/{majorId}")
    suspend fun getMajorInfo(
        @Path("majorId") majorId: Int
    ) : ResponseMajorData

    @GET("review/{postId}")
    suspend fun getReviewDetail(
        @Path("postId") postId: Int
    ) : ResponseReviewDetailData

    @DELETE("review/{postId}")
    suspend fun deleteReview(
        @Path("postId") postId: Int
    ) : ResponseDeleteReview

    @PUT("review/{postId}")
    suspend fun putReview(
        @Path("postId") postId: Int,
        @Body requestBody: RequestPutReviewData
    ) : ResponsePutReviewData

    @POST("review")
    suspend fun postReview(
        @Body requestBody: RequestPostReviewData
    ) : ResponsePostReviewData

}