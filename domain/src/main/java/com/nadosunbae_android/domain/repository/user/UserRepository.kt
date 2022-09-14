package com.nadosunbae_android.domain.repository.user

import com.nadosunbae_android.domain.model.user.UserInfoData
import com.nadosunbae_android.domain.model.user.UserLikeData
import com.nadosunbae_android.domain.model.user.UserPostData
import com.nadosunbae_android.domain.model.user.UserReviewData

interface UserRepository {
    suspend fun getUserPost(filter : String): List<UserPostData>

    suspend fun getUserInfo(userId : Int) : UserInfoData

    suspend fun getUserComment(filter : String) : List<UserPostData>

    suspend fun getUserReview(userId : Int) : List<UserReviewData.Review>

    suspend fun getUserLike(filter : String) : List<UserLikeData>
}