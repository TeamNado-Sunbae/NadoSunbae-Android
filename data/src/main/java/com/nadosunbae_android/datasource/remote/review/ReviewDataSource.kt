package com.nadosunbae_android.datasource.remote.review

import com.nadosunbae_android.data.model.request.review.RequestPutReview
import com.nadosunbae_android.data.model.response.review.ResponseDeleteReview
import com.nadosunbae_android.data.model.response.review.ResponsePutReview
import com.nadosunbae_android.model.request.review.RequestPostReview
import com.nadosunbae_android.model.request.review.RequestReviewListData
import com.nadosunbae_android.model.response.review.*
import retrofit2.Response

interface ReviewDataSource {

    suspend fun getReviewList(
        sort: String = "recent", body: RequestReviewListData) : ResponseReviewListData

    suspend fun getMajorInfo(majorId: Int) : ResponseMajorData

    suspend fun getReviewDetail(postId: Int) : ResponseReviewDetailData

    suspend fun deleteReview(postId: Int) : ResponseDeleteReview

    suspend fun putReview(postId: Int, requestBody: RequestPutReview) : ResponsePutReview

    suspend fun getBackgroundImageList() : ResponseBackgroundImageListData

    suspend fun postReview(requestBody: RequestPostReview) : ResponsePostReview

}