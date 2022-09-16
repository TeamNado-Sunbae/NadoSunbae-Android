package com.nadosunbae_android.data.datasource.remote.user

import com.nadosunbae_android.data.model.response.user.*

interface UserDataSource {
    suspend fun getUserPost(filter : String) : ResponseUserPost

    suspend fun getUserInfo(userId : Int) : ResponseUserInfo

    suspend fun getUserComment(filter : String) : ResponseUserPost

    suspend fun getUserReview(userId : Int) : ResponseUserReview

    suspend fun getUserLike(filter : String) : ResponseUserLike

    suspend fun getUserQuestion(userId: Int, sort: String) : ResponseUserQuestion
}