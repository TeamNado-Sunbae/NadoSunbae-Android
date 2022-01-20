package com.nadosunbae_android.data.repository.review

import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSource
import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSourceImpl
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseMajorData
import com.nadosunbae_android.data.model.response.review.ResponseReviewDetailData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import retrofit2.Response

class ReviewRepositoryImpl : ReviewRepository {
    val reviewDataSource : ReviewDataSource = ReviewDataSourceImpl()

    override fun getReviewList(
        sort: String,
        body: RequestReviewListData,
        onResponse: (Response<ResponseReviewListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return reviewDataSource.getReviewList(sort, body, onResponse, onFailure)
    }

    override fun getMajorInfo(
        majorId: Int,
        onResponse: (Response<ResponseMajorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return reviewDataSource.getMajorInfo(majorId, onResponse, onFailure)
    }

    override fun getReviewDetail(
        postId: Int,
        onResponse: (Response<ResponseReviewDetailData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return reviewDataSource.getReviewDetail(postId, onResponse, onFailure)
    }

}