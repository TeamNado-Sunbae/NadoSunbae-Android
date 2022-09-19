package com.nadosunbae_android.data.repositoryimpl.review

import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSource
import com.nadosunbae_android.data.mapper.review.ReviewMapper
import com.nadosunbae_android.domain.model.review.*
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(private val dataSource: ReviewDataSource) : ReviewRepository {

    override suspend fun getReviewListByMajor(
        reviewFilterItem: ReviewFilterItem,
        sort: String
    ): List<ReviewPreviewData> {
        return ReviewMapper.mapperToReviewPreviewData(dataSource.getReviewListByMajor(
            reviewFilterItem.majorId,
            sort,
            reviewFilterItem.tagFilter,
            reviewFilterItem.writerFilter
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
}