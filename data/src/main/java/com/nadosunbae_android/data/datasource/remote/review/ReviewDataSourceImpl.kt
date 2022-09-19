package com.nadosunbae_android.data.datasource.remote.review

import com.nadosunbae_android.data.api.review.ReviewService
import com.nadosunbae_android.data.model.request.review.RequestPutReviewData
import com.nadosunbae_android.data.model.response.review.ResponseDeleteReview
import com.nadosunbae_android.data.model.response.review.ResponsePutReviewData
import com.nadosunbae_android.data.model.request.review.RequestPostReviewData
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.*
import javax.inject.Inject

class ReviewDataSourceImpl @Inject construcor(private val service : ReviewService) : 
    ReviewDataSource {
    override suspend fun getReviewListByMajor(
        majorId: Int,
        sort: String,
        tagFilter: List<Int>,
        writerFilter: String
    ): ResponseReviewListData {
        return service.getReviewListByMajor(majorId, sort, tagFilter, writerFilter)
    }

    override suspend fun getReviewListByUniv(univId: Int): ResponseReviewListData {
        return service.getReviewListByUniv(univId)
    }

    override suspend fun getMajorInfo(majorId: Int): ResponseMajorData {
        return service.getMajorInfo(majorId)
    }

    override suspend fun getReviewDetail(postId: Int): ResponseReviewDetailData {
        return service.getReviewDetail(postId)
    }

    override suspend fun deleteReview(postId: Int): ResponseDeleteReview {
        return service.deleteReview(postId)
    }

    override suspend fun putReview(
        postId: Int,
        requestBody: RequestPutReviewData
    ): ResponsePutReviewData {
        return service.putReview(postId, requestBody)
    }

    override suspend fun postReview(requestBody: RequestPostReviewData): ResponsePostReviewData {
        return service.postReview(requestBody)
    }
}