package com.nadosunbae_android.data.mapper.user

import android.util.Log
import com.nadosunbae_android.data.model.response.user.*
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.model.user.*

object UserMapper {
    fun mapperToUserPostData(responseUserPost: ResponseUserPost): List<UserPostData> {
        return responseUserPost.data.postList.map {
            UserPostData(
                commentCount = it.commentCount,
                content = it.content,
                createdAt = it.createdAt,
                isLiked = it.like.isLiked,
                likeCount = it.like.likeCount,
                majorName = it.majorName,
                postId = it.postId,
                title = it.title,
                type = it.type,
                writerId = it.writer.id,
                nickname = it.writer.nickname
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

    fun mapperToUserReview(responseUserReview: ResponseUserReview): List<UserReviewData.Review> {
        return responseUserReview.data.reviewList.map {
            UserReviewData.Review(
                createdAt = it.createdAt,
                id = it.id,
                majorName = it.majorName,
                oneLineReview = it.oneLineReview,
                tagName = it.tagList.map {
                    it.tagName
                },
                isLiked = it.like.isLiked,
                likeCount = it.like.likeCount,
                nickname = responseUserReview.data.writer.nickname,
                writerId = responseUserReview.data.writer.writerId
            )
        }
    }

    fun mapperToUserLike(responseUserLike: ResponseUserLike) : List<UserLikeData> {
        return responseUserLike.data.likeList.map {
            UserLikeData(
                commentCount = it.commentCount,
                content = it.content,
                createdAt = it.createdAt,
                id = it.id,
                majorName = it.majorName,
                title = it.title,
                type = it.type,
                isLiked = it.like?.isLiked,
                likeCount = it.like?.likeCount,
                writerId = it.writer?.id,
                nickname = it.writer?.nickname,
                tagName = it.tagList?.map {
                    it.tagName
                }
            )

        }
    }

    fun mapperToUserQuestion(responseUserQuestion: ResponseUserQuestion) : List<UserQuestionData> {
        return responseUserQuestion.data.postList.map {
            UserQuestionData(
                commentCount = it.commentCount,
                content = it.content,
                createdAt = it.createdAt,
                postId = it.postId,
                title = it.title,
                isLiked = it.like.isLiked,
                likeCount = it.like.likeCount,
                id = it.writer.id,
                nickname = it.writer.nickname
            )
        }
    }

    fun mapperToSeniorInfoList(responseSeniorList: ResponseSeniorList): ClassRoomSeniorData {
        val onQuestionUsers = responseSeniorList.data.onQuestionUserList.map {
            ClassRoomSeniorData.UserSummaryData(
                id = it.id,
                profileImageId = it.profileImageId,
                isFirstMajor = it.isFirstMajor,
                isOnQuestion = it.isOnQuestion,
                majorStart = it.majorStart,
                nickname = it.nickname,
                rate = it.rate
            )
        }

        val offQuestionUsers = responseSeniorList.data.offQuestionUserList.map {
            ClassRoomSeniorData.UserSummaryData(
                id = it.id,
                profileImageId = it.profileImageId,
                isFirstMajor = it.isFirstMajor,
                isOnQuestion = it.isOnQuestion,
                majorStart = it.majorStart,
                nickname = it.nickname,
                rate = it.rate
            )
        }

        return ClassRoomSeniorData(
             onQuestionUserList = onQuestionUsers,
            offQuestionUserList = offQuestionUsers
        )
    }
}