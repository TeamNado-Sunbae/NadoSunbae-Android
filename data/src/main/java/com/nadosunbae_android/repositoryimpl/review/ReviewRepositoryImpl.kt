package com.nadosunbae_android.repositoryimpl.review

import com.nadosunbae_android.datasource.remote.review.ReviewDataSource
import com.nadosunbae_android.mapper.review.ReviewMapper
import com.nadosunbae_android.model.review.*
import com.nadosunbae_android.repository.review.ReviewRepository

class ReviewRepositoryImpl(private val dataSource: ReviewDataSource) : ReviewRepository {

    override suspend fun getReviewList(
        reviewFilterItem: ReviewFilterItem,
        sort: String
    ): List<ReviewPreviewData> {
        return ReviewMapper.mapperToReviewPreviewData(dataSource.getReviewList(sort,
            ReviewMapper.mapperToReviewFilterItem(reviewFilterItem)
        ))
    }

    override suspend fun getMajorInfo(majorId: Int): MajorInfoData {
        return ReviewMapper.mapperToMajorData(dataSource.getMajorInfo(majorId))
    }

    override suspend fun getReviewDetail(postId: Int): ReviewDetailData {
        return ReviewMapper.mapperToReviewDetailData(dataSource.getReviewDetail(postId))
    }

    override suspend fun postReview(reviewWriteItem: ReviewWriteItem): ReviewWriteData {
        return ReviewMapper.mapperToReviewWriteData(dataSource.postReview(
            ReviewMapper.mapperToReviewWriteItem(reviewWriteItem)
        ))
    }

    override suspend fun putReview(postId: Int, reviewEditItem: ReviewEditItem): ReviewWriteData {
        return ReviewMapper.mapperToReviewEditData(dataSource.putReview(postId,
            ReviewMapper.mapperToReviewEditItem(reviewEditItem)
        ))
    }

    override suspend fun deleteReview(postId: Int): ReviewDeleteData {
        return ReviewMapper.mapperToReviewDeleteData(dataSource.deleteReview(postId))
    }

    override suspend fun getBackgroundImageList(): List<BackgroundImageData> {
        return ReviewMapper.mapperToBackgroundImageData(dataSource.getBackgroundImageList())
    }

}