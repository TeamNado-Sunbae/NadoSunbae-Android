package com.nadosunbae_android.data.api.user

import com.nadosunbae_android.data.model.response.user.ResponseUserInfo
import com.nadosunbae_android.data.model.response.user.ResponseUserPost
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
}