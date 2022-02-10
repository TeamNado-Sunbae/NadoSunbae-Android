package com.nadosunbae_android.datasource.remote.review

import com.nadosunbae_android.api.review.ReviewService
import com.nadosunbae_android.data.model.request.review.RequestPutReviewData
import com.nadosunbae_android.data.model.response.review.ResponseDeleteReview
import com.nadosunbae_android.data.model.response.review.ResponsePutReviewData
import com.nadosunbae_android.model.request.review.RequestPostReviewData
import com.nadosunbae_android.model.request.review.RequestReviewListData
import com.nadosunbae_android.model.response.review.*

class ReviewDataSourceImpl(private val service : ReviewService) : ReviewDataSource {
    override suspend fun getReviewList(
        sort: String,
        body: RequestReviewListData
    ): ResponseReviewListData {
        return service.getReviewList(sort,body)
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

    override suspend fun putReview(postId: Int, requestBody: RequestPutReviewData): ResponsePutReviewData {
        return service.putReview(postId, requestBody)
    }

    override suspend fun getBackgroundImageList(): ResponseBackgroundImageListData {
        return service.getBackgroundImageList()
    }

    override suspend fun postReview(requestBody: RequestPostReviewData): ResponsePostReviewData {
        return service.postReview(requestBody)
    }
}