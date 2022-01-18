package com.nadosunbae_android.data.repository.review

import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import retrofit2.Response

interface ReviewRepository {
    fun getReviewList(
        token: String, sort: String = "recent", body: RequestReviewListData,
        onResponse: (Response<ResponseReviewListData>) -> Unit,
        onFailure: (Throwable) -> Unit
        )
}