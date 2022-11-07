package com.nadosunbae_android.data.datasource.remote.review

import com.nadosunbae_android.data.model.request.review.RequestPutReviewData
import com.nadosunbae_android.data.model.response.review.ResponseDeleteReview
import com.nadosunbae_android.data.model.response.review.ResponsePutReviewData
import com.nadosunbae_android.data.model.request.review.RequestPostReviewData
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.*

interface ReviewDataSource {

    suspend fun getReviewListByMajor(
        majorId: Int,
        sort: String = "recent",
        tagFilter: List<Int>,
        writerFilter: String
    ) : ResponseReviewListData

    suspend fun getReviewListByUniv(
        univId: Int
    ) : ResponseReviewListData

    suspend fun getMajorInfo(majorId: Int) : ResponseMajorData

    suspend fun getReviewDetail(postId: Int) : ResponseReviewDetailData

    suspend fun deleteReview(postId: Int) : ResponseDeleteReview

    suspend fun putReview(postId: Int, requestBody: RequestPutReviewData) : ResponsePutReviewData

    suspend fun postReview(requestBody: RequestPostReviewData) : ResponsePostReviewData

}