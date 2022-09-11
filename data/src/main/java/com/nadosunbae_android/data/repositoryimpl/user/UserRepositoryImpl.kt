package com.nadosunbae_android.data.repositoryimpl.user

import com.nadosunbae_android.data.datasource.remote.user.UserDataSource
import com.nadosunbae_android.data.mapper.user.UserMapper
import com.nadosunbae_android.domain.model.user.UserPostData
import com.nadosunbae_android.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) : UserRepository {
    override suspend fun getUserPost(filter: String): List<UserPostData> {
        return UserMapper.mapperToUserPostData(userDataSource.getUserPost(filter))
    }

}