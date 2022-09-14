package com.nadosunbae_android.data.api.user

import com.nadosunbae_android.data.model.response.user.ResponseUserInfo
import com.nadosunbae_android.data.model.response.user.ResponseUserLike
import com.nadosunbae_android.data.model.response.user.ResponseUserPost
import com.nadosunbae_android.data.model.response.user.ResponseUserReview
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
}