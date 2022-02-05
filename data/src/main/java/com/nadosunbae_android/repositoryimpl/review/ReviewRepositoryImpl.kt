package com.nadosunbae_android.repositoryimpl.review

import com.nadosunbae_android.datasource.remote.review.ReviewDataSource
import com.nadosunbae_android.datasource.remote.review.ReviewDataSourceImpl
import com.nadosunbae_android.model.request.review.RequestPostReviewData
import com.nadosunbae_android.model.request.review.RequestReviewListData
import com.nadosunbae_android.model.response.review.*
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
        body: RequestPutReviewData,
        onResponse: (Response<ResponsePutReviewData>) -> Unit,
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
        requestBody: RequestPostReviewData,
        onResponse: (Response<ResponsePostReviewData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return reviewDataSource.postReview(requestBody, onResponse, onFailure)
    }

}