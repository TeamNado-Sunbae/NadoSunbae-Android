package com.nadosunbae_android.domain.repository.user

import com.nadosunbae_android.domain.model.user.UserInfoData
import com.nadosunbae_android.domain.model.user.UserPostData

interface UserRepository {
    suspend fun getUserPost(filter : String): List<UserPostData>

    suspend fun getUserInfo(userId : Int) : UserInfoData
}