package com.nadosunbae_android.data.repository.review

import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSource
import com.nadosunbae_android.data.datasource.remote.review.ReviewDataSourceImpl
import com.nadosunbae_android.data.model.request.review.RequestPostReview
import com.nadosunbae_android.data.model.request.review.RequestPutReview
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.*
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

    override fun deleteReview(
        postId: Int,
        onResponse: (Response<ResponseDeleteReview>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return reviewDataSource.deleteReview(postId, onResponse, onFailure)
    }

    override fun putReview(
        postId: Int,
        body: RequestPutReview,
        onResponse: (Response<ResponsePutReview>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return reviewDataSource.putReview(postId, body, onResponse, onFailure)
    }

    override fun getBackgroundImageList(
        onResponse: (Response<ResponseBackgroundImageListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return reviewDataSource.getBackgroundImageList(onResponse, onFailure)
    }

    override fun postReview(
        requestBody: RequestPostReview,
        onResponse: (Response<ResponsePostReview>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return reviewDataSource.postReview(requestBody, onResponse, onFailure)
    }

}