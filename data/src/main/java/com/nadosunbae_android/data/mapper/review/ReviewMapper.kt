package com.nadosunbae_android.data.mapper.review

import com.nadosunbae_android.data.model.request.review.RequestPutReviewData
import com.nadosunbae_android.data.model.response.review.ResponseDeleteReview
import com.nadosunbae_android.data.model.response.review.ResponsePutReviewData
import com.nadosunbae_android.data.model.request.review.RequestPostReviewData
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.*
import com.nadosunbae_android.domain.model.review.*
import java.util.*

object ReviewMapper {

    // 후기목록 불러오기 request
    fun mapperToReviewFilterItem(reviewFilterItem: ReviewFilterItem): RequestReviewListData {
        return RequestReviewListData(
            majorId = reviewFilterItem.majorId,
            writerFilter = reviewFilterItem.writerFilter,
            tagFilter = reviewFilterItem.tagFilter
        )
    }

    // 후기목록 불러오기 response
    fun mapperToReviewPreviewData(responseReviewListData: ResponseReviewListData): List<ReviewPreviewData> {
        return responseReviewListData.data.map {
            ReviewPreviewData(
                createdAt = it.createdAt,
                likeCount = it.like.likeCount,
                isLiked = it.like.isLiked,
                oneLineReview = it.oneLineReview,
                postId = it.postId,
                tagList = it.tagList.map {
                    it.tagName
                },
                writerId = it.writer.writerId,
                firstMajorName = it.writer.firstMajorName,
                firstMajorStart = it.writer.firstMajorStart,
                nickname = it.writer.nickname,
                profileImageId = it.writer.profileImageId,
                secondMajorName = it.writer.secondMajorName,
                secondMajorStart = it.writer.secondMajorStart,

            )
        }
    }

    // 후기 상세보기 response
    fun mapperToReviewDetailData(responseReviewDetailData: ResponseReviewDetailData): ReviewDetailData {
        return ReviewDetailData(
            backgroundImageId = responseReviewDetailData.data.backgroundImage.imageId,
            backgroundImageUrl = responseReviewDetailData.data.backgroundImage.imageUrl,
            isLiked = responseReviewDetailData.data.like.isLiked,
            likeCount = responseReviewDetailData.data.like.likeCount,
            contentList = responseReviewDetailData.data.post.contentList.map {
                ReviewDetailData.Content(
                    title = it.title,
                    content = it.content
                )
            },
            createdAt = responseReviewDetailData.data.post.createdAt,
            oneLineReview = responseReviewDetailData.data.post.oneLineReview,
            postId = responseReviewDetailData.data.post.postId,
            firstMajorStart = responseReviewDetailData.data.writer.firstMajorStart,
            firstMajorName = responseReviewDetailData.data.writer.firstMajorName,
            isOnQuestion = responseReviewDetailData.data.writer.isOnQuestion,
            isReviewed = responseReviewDetailData.data.writer.isReviewd,
            nickname = responseReviewDetailData.data.writer.nickname,
            profileImageId = responseReviewDetailData.data.writer.profileImageId,
            secondMajorStart = responseReviewDetailData.data.writer.secondMajorStart,
            secondMajorName = responseReviewDetailData.data.writer.secondMajorName,
            writerId = responseReviewDetailData.data.writer.writerId
        )
    }


    // 후기 작성하기 request
    fun mapperToReviewWriteItem(reviewWriteItem: ReviewWriteItem): RequestPostReviewData {
        return RequestPostReviewData(
            majorId = reviewWriteItem.majorId,
            backgroundImageId = reviewWriteItem.backgroundImageId,
            oneLineReview = reviewWriteItem.oneLineReview,
            prosCons = reviewWriteItem.prosCons,
            curriculum = reviewWriteItem.curriculum,
            recommendLecture = reviewWriteItem.recommendLecture,
            nonRecommendLecture = reviewWriteItem.nonRecommendLecture,
            career = reviewWriteItem.career,
            tip = reviewWriteItem.tip
        )
    }

    // 후기 작성하기 response
    fun mapperToReviewWriteData(responsePostReviewData: ResponsePostReviewData): ReviewWriteData {
        return ReviewWriteData(
            success = responsePostReviewData.success,
            postId = responsePostReviewData.data.post.postId,
            createdAt = responsePostReviewData.data.post.createdAt,
            oneLineReview = responsePostReviewData.data.post.oneLineReview,
            contentList = responsePostReviewData.data.post.contentList.map {
                ReviewWriteData.Content(
                    title = it.title,
                    content = it.content
                )
            },
            backgroundImageId = responsePostReviewData.data.backgroundImage.imageId,
            backgroundImageUrl = responsePostReviewData.data.backgroundImage.imageUrl.imageUrl,
            isLiked = responsePostReviewData.data.like.isLiked,
            likeCount = responsePostReviewData.data.like.likeCount,
            writerId = responsePostReviewData.data.writer.writerId
        )
    }


    // 후기 수정하기 request
    fun mapperToReviewEditItem(reviewEditItem: ReviewEditItem): RequestPutReviewData {
        return RequestPutReviewData(
            backgroundImageId = reviewEditItem.backgroundImageId,
            oneLineReview = reviewEditItem.oneLineReview,
            prosCons = reviewEditItem.prosCons,
            curriculum = reviewEditItem.curriculum,
            career = reviewEditItem.career,
            recommendLecture = reviewEditItem.recommendLecture,
            nonRecommendLecture = reviewEditItem.nonRecommendLecture,
            tip = reviewEditItem.tip
        )
    }

    // 후기 수정하기 response
    fun mapperToReviewEditData(responsePutReviewData: ResponsePutReviewData): ReviewWriteData {
        return ReviewWriteData(
            success = responsePutReviewData.success,
            postId = responsePutReviewData.data.post.postId,
            createdAt = responsePutReviewData.data.post.createdAt,
            oneLineReview = responsePutReviewData.data.post.oneLineReview,
            contentList = responsePutReviewData.data.post.contentList.map {
                ReviewWriteData.Content(
                    title = it.title,
                    content = it.content
                )
            },
            backgroundImageId = responsePutReviewData.data.backgroundImage.imageId,
            backgroundImageUrl = responsePutReviewData.data.backgroundImage.imageUrl,
            isLiked = responsePutReviewData.data.like.isLiked,
            likeCount = responsePutReviewData.data.like.likeCount,
            writerId = responsePutReviewData.data.writer.writerId
        )
    }

    // 후기 삭제하기 response
    fun mapperToReviewDeleteData(responseDeleteReview: ResponseDeleteReview): ReviewDeleteData {
        return ReviewDeleteData(
            isDeleted = responseDeleteReview.data.isDeleted,
            isReviewed = responseDeleteReview.data.isReviewed,
            postId = responseDeleteReview.data.postId
        )
    }



    // 학과정보 불러오기 response
    fun mapperToMajorData(responseMajorData: ResponseMajorData): MajorInfoData {
        return MajorInfoData(
            homepage = responseMajorData.data.homepage,
            majorName = responseMajorData.data.majorName,
            subjectTable = responseMajorData.data.subjectTable
        )
    }

    // 후기 배경목록 불러오기 response
    fun mapperToBackgroundImageData(responseBackgroundImageListData: ResponseBackgroundImageListData): List<BackgroundImageData> {
        return responseBackgroundImageListData.data.backgroundImageList.map {
            BackgroundImageData(
                imageId = it.imageId,
                imageUrl = it.imageUrl
            )
        }
    }


}