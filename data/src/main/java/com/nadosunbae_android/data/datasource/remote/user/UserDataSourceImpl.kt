package com.nadosunbae_android.data.datasource.remote.user

import com.nadosunbae_android.data.api.user.UserService
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.user.*
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val service: UserService) : UserDataSource{
    override suspend fun getUserPost(filter: String): ResponseUserPost {
        return service.getMyPost(filter)
    }

    override suspend fun getUserInfo(userId: Int): ResponseUserInfo {
        return service.getUserInfo(userId)
    }

    override suspend fun getUserComment(filter: String): ResponseUserPost {
        return service.getMyComment(filter)
    }

    override suspend fun getUserReview(userId: Int): ResponseUserReview {
        return service.getUserReview(userId)
    }

    override suspend fun getUserLike(filter: String): ResponseUserLike {
        return service.getUserLike(filter)
    }

    override suspend fun getUserQuestion(userId: Int, sort: String): ResponseUserQuestion {
        return service.getUserQuestion(userId, sort)
    }

    override suspend fun getSeniorList(majorId: Int, exclude: String): ResponseSeniorList {
        return service.getSeniorList(majorId, exclude)
    }


}