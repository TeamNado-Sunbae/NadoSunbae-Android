package com.nadosunbae_android.data.mapper.user

import com.nadosunbae_android.data.model.response.user.ResponseUserInfo
import com.nadosunbae_android.data.model.response.user.ResponseUserPost
import com.nadosunbae_android.domain.model.user.UserInfoData
import com.nadosunbae_android.domain.model.user.UserPostData

object UserMapper {
    fun mapperToUserPostData(responseUserPost: ResponseUserPost): List<UserPostData> {
        return responseUserPost.data.postList.map {
            UserPostData(
                commentCount = it.commentCount,
                content = it.content,
                createdAt = it.createdAt,
                like = UserPostData.Like(
                    isLiked = it.like.isLiked,
                    likeCount = it.like.likeCount
                ),
                majorName = it.majorName,
                postId = it.postId,
                title = it.title,
                type = it.type,
                writer = UserPostData.Writer(
                    id = it.writer.id,
                    nickname = it.writer.nickname
                )
            )
        }
    }

    // 내 정보
    fun mapperToMyInfo(responseUserInfo: ResponseUserInfo): UserInfoData {
        return UserInfoData(
            bio = responseUserInfo.data.bio,
            count = responseUserInfo.data.count,
            firstMajorName = responseUserInfo.data.firstMajorName,
            firstMajorStart = responseUserInfo.data.firstMajorStart,
            isOnQuestion = responseUserInfo.data.isOnQuestion,
            nickname = responseUserInfo.data.nickname,
            profileImageId = responseUserInfo.data.profileImageId,
            responseRate = responseUserInfo.data.responseRate,
            secondMajorName = responseUserInfo.data.secondMajorName,
            secondMajorStart = responseUserInfo.data.secondMajorStart,
            userId = responseUserInfo.data.userId
        )
    }
}