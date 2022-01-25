package com.nadosunbae_android.datasource.remote.review

import com.nadosunbae_android.model.request.review.RequestPostReview
import com.nadosunbae_android.model.request.review.RequestReviewListData
import com.nadosunbae_android.model.response.review.*
import retrofit2.Response

interface ReviewDataSource {

    suspend fun getReviewList(
        sort: String = "recent", body: RequestReviewListData) : ResponseReviewListData

    suspend fun getMajorInfo(majorId: Int) : ResponseMajorData

    suspend fun getReviewDetail(postId: Int) : ResponseReviewDetailData

    suspend fun getBackgroundImageList() : ResponseBackgroundImageListData

    suspend fun postReview(requestBody: RequestPostReview) : ResponsePostReview

}