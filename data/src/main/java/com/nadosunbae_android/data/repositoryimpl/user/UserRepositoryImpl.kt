package com.nadosunbae_android.data.repositoryimpl.user

import com.nadosunbae_android.data.datasource.remote.user.UserDataSource
import com.nadosunbae_android.data.mapper.user.UserMapper
import com.nadosunbae_android.domain.model.user.UserInfoData
import com.nadosunbae_android.domain.model.user.UserLikeData
import com.nadosunbae_android.domain.model.user.UserPostData
import com.nadosunbae_android.domain.model.user.UserReviewData
import com.nadosunbae_android.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) : UserRepository {
    override suspend fun getUserPost(filter: String): List<UserPostData> {
        return UserMapper.mapperToUserPostData(userDataSource.getUserPost(filter))
    }

    override suspend fun getUserInfo(userId: Int): UserInfoData {
        return UserMapper.mapperToMyInfo(userDataSource.getUserInfo(userId))
    }

    override suspend fun getUserComment(filter: String): List<UserPostData> {
        return UserMapper.mapperToUserPostData(userDataSource.getUserComment(filter))
    }

    override suspend fun getUserReview(userId: Int): List<UserReviewData.Review> {
        return UserMapper.mapperToUserReview(userDataSource.getUserReview(userId))
    }

    override suspend fun getUserLike(filter: String): List<UserLikeData> {
        return UserMapper.mapperToUserLike(userDataSource.getUserLike(filter))
    }

}