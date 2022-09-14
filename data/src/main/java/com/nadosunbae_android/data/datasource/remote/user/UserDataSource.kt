package com.nadosunbae_android.data.datasource.remote.user

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.user.ResponseUserInfo
import com.nadosunbae_android.data.model.response.user.ResponseUserLike
import com.nadosunbae_android.data.model.response.user.ResponseUserPost
import com.nadosunbae_android.data.model.response.user.ResponseUserReview

interface UserDataSource {
    suspend fun getUserPost(filter : String) : ResponseUserPost

    suspend fun getUserInfo(userId : Int) : ResponseUserInfo

    suspend fun getUserComment(filter : String) : ResponseUserPost

    suspend fun getUserReview(userId : Int) : ResponseUserReview

    suspend fun getUserLike(filter : String) : ResponseUserLike
}