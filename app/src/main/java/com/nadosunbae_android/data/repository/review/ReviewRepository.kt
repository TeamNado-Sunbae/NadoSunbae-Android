package com.nadosunbae_android.data.repository.review

import com.nadosunbae_android.data.model.request.review.RequestPostReview
import com.nadosunbae_android.data.model.request.review.RequestPutReview
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.*
import retrofit2.Response

interface ReviewRepository {

    fun getReviewList(sort: String = "recent", body: RequestReviewListData,
        onResponse: (Response<ResponseReviewListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun getMajorInfo(majorId: Int,
         onResponse: (Response<ResponseMajorData>) -> Unit,
         onFailure: (Throwable) -> Unit
     )

    fun getReviewDetail(postId: Int,
        onResponse: (Response<ResponseReviewDetailData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun deleteReview(postId: Int,
        onResponse: (Response<ResponseDeleteReview>) -> Unit,
        onFailure: (Throwable) -> Unit
     )

    fun putReview(postId: Int, body: RequestPutReview,
        onResponse: (Response<ResponsePutReview>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun getBackgroundImageList(
        onResponse: (Response<ResponseBackgroundImageListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun postReview(requestBody: RequestPostReview,
       onResponse: (Response<ResponsePostReview>) -> Unit,
       onFailure: (Throwable) -> Unit
    )

}