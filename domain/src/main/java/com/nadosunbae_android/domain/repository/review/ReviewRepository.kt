package com.nadosunbae_android.domain.repository.review

import com.nadosunbae_android.data.model.review.*

interface ReviewRepository {

    // 리뷰 목록 불러오기
    suspend fun getReviewList(reviewFilterItem: ReviewFilterItem, sort: String = "recent"): List<ReviewPreviewData>

    // 학과정보 불러오기
    suspend fun getMajorInfo(majorId: Int): MajorInfoData

    // 후기 상세정보 불러오기
    suspend fun getReviewDetail(postId: Int): ReviewDetailData

    // 후기 작성
    suspend fun postReview(reviewWriteItem: ReviewWriteItem): ReviewWriteData

    // 후기 수정
    suspend fun putReview(postId: Int, reviewEditItem: ReviewEditItem): ReviewWriteData

    // 후기 삭제
    suspend fun deleteReview(postId: Int): ReviewDeleteData

    // 후기 배경목록 불러오기
    suspend fun getBackgroundImageList(): List<BackgroundImageData>

}