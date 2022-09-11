package com.nadosunbae_android.data.datasource.remote.user

import com.nadosunbae_android.data.api.user.UserService
import com.nadosunbae_android.data.model.response.user.ResponseUserPost
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val service: UserService) : UserDataSource{
    override suspend fun getUserPost(filter: String): ResponseUserPost {
        return service.getMyPost(filter)
    }

}