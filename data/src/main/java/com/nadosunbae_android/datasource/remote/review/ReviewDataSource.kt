package com.nadosunbae_android.datasource.remote.review

import com.nadosunbae_android.data.model.request.review.RequestPutReviewData
import com.nadosunbae_android.data.model.response.review.ResponseDeleteReview
import com.nadosunbae_android.data.model.response.review.ResponsePutReviewData
import com.nadosunbae_android.model.request.review.RequestPostReviewData
import com.nadosunbae_android.model.request.review.RequestReviewListData
import com.nadosunbae_android.model.response.review.*

interface ReviewDataSource {

    suspend fun getReviewList(
        sort: String = "recent", body: RequestReviewListData) : ResponseReviewListData

    suspend fun getMajorInfo(majorId: Int) : ResponseMajorData

    suspend fun getReviewDetail(postId: Int) : ResponseReviewDetailData

    suspend fun deleteReview(postId: Int) : ResponseDeleteReview

    suspend fun putReview(postId: Int, requestBody: RequestPutReviewData) : ResponsePutReviewData

    suspend fun getBackgroundImageList() : ResponseBackgroundImageListData

    suspend fun postReview(requestBody: RequestPostReviewData) : ResponsePostReviewData

}