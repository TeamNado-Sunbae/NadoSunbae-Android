package com.nadosunbae_android.domain.repository.user

import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.model.user.*
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserPost(filter: String): List<UserPostData>

    suspend fun getUserInfo(userId: Int): UserInfoData

    suspend fun getUserComment(filter: String): List<UserPostData>

    suspend fun getUserReview(userId: Int): List<UserReviewData.Review>

    suspend fun getUserLike(filter: String): List<UserLikeData>

    suspend fun getUserQuestion(userId: Int, sort: String): List<UserQuestionData>

    suspend fun getSeniorList(majorId: Int, exclude: String): Flow<SeniorListData>
}