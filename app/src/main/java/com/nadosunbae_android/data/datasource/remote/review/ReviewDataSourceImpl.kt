package com.nadosunbae_android.data.datasource.remote.review

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.model.request.review.RequestPostReview
import com.nadosunbae_android.data.model.request.review.RequestPutReview
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.*
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class ReviewDataSourceImpl() : ReviewDataSource {

    override fun getReviewList(
        sort: String,
        body: RequestReviewListData,
        onResponse: (Response<ResponseReviewListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.getReviewList(sort, body).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun getMajorInfo(
        majorId: Int,
        onResponse: (Response<ResponseMajorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.getMajorInfo(majorId).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun getReviewDetail(
        postId: Int,
        onResponse: (Response<ResponseReviewDetailData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.getReviewDetail(postId).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun deleteReview(
        postId: Int,
        onResponse: (Response<ResponseDeleteReview>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.deleteReview(postId).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun putReview(
        postId: Int,
        requestBody: RequestPutReview,
        onResponse: (Response<ResponsePutReview>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.putReview(postId, requestBody).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun getBackgroundImageList(
        onResponse: (Response<ResponseBackgroundImageListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.getBackgroundImageList().enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun postReview(
        requestBody: RequestPostReview,
        onResponse: (Response<ResponsePostReview>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.postReview(requestBody).enqueueUtil(
            onResponse, onFailure
        )
    }

}