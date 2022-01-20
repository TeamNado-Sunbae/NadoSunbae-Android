package com.nadosunbae_android.data.repository.review

import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseBackgroundImageListData
import com.nadosunbae_android.data.model.response.review.ResponseMajorData
import com.nadosunbae_android.data.model.response.review.ResponseReviewDetailData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
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

    fun getBackgroundImageList(
        onResponse: (Response<ResponseBackgroundImageListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

}