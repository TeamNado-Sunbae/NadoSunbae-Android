package com.nadosunbae_android.data.api.user

import com.nadosunbae_android.data.model.response.user.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("user/post")
    suspend fun getMyPost(
        @Query("filter") filter: String
    ): ResponseUserPost

    @GET("user/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: Int
    ): ResponseUserInfo

    @GET("user/comment")
    suspend fun getMyComment(
        @Query("filter") filter: String
    ): ResponseUserPost

    @GET("user/{userId}/review")
    suspend fun getUserReview(
        @Path("userId") userId: Int
    ): ResponseUserReview

    @GET("user/like")
    suspend fun getUserLike(
        @Query("filter") filter : String
    ) : ResponseUserLike

    @GET("user/{userId}/post/question")
    suspend fun getUserQuestion(
        @Path("userId") userId : Int,
        @Query("sort") sort : String
    ) : ResponseUserQuestion
}