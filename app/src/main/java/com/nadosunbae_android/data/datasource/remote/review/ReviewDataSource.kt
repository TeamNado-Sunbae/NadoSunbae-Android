package com.nadosunbae_android.data.datasource.remote.review

import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseMajorData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import retrofit2.Response

interface ReviewDataSource {

    fun getReviewList(
        sort: String = "recent", body: RequestReviewListData,
        onResponse: (Response<ResponseReviewListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun getMajorInfo(
        majorId: Int,
        onResponse: (Response<ResponseMajorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

}