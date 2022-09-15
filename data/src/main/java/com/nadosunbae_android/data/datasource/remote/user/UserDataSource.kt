package com.nadosunbae_android.data.datasource.remote.user

import com.nadosunbae_android.data.model.response.user.ResponseUserInfo
import com.nadosunbae_android.data.model.response.user.ResponseUserPost

interface UserDataSource {
    suspend fun getUserPost(filter : String) : ResponseUserPost

    suspend fun getUserInfo(userId : Int) : ResponseUserInfo
}