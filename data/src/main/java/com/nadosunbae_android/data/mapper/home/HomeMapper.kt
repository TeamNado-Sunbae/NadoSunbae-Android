package com.nadosunbae_android.data.mapper.home

import com.nadosunbae_android.data.model.response.home.ResponseHomeRanking
import com.nadosunbae_android.data.model.response.home.ResponseUnivReview
import com.nadosunbae_android.domain.model.home.HomeRankingData
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData

object HomeMapper {
    fun mapperToUnviReview(responseUnivReview: ResponseUnivReview): List<HomeUnivReviewData> {
        return responseUnivReview.data.map {
            HomeUnivReviewData(
                createdAt = it.createdAt,
                id = it.id,
                majorName = it.majorName,
                oneLineReview = it.oneLineReview,
                tagList = it.tagList.map {
                    it.tagName
                },
                isLiked = it.like.isLiked,
                likeCount = it.like.likeCount
            )
        }
    }

    fun mapperToRanking(responseHomeRanking: ResponseHomeRanking) : List<HomeRankingData> {
        return responseHomeRanking.data.userList.map {
            HomeRankingData(
                firstMajorName = it.firstMajorName,
                firstMajorStart = it.firstMajorStart,
                id = it.id,
                nickname = it.nickname,
                profileImageId = it.profileImageId,
                rate = it.rate,
                secondMajorName = it.secondMajorName,
                secondMajorStart = it.secondMajorStart
            )
        }
    }
}