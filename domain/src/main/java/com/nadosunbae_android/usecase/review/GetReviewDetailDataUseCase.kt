package com.nadosunbae_android.usecase.review

import com.nadosunbae_android.model.review.ReviewDetailData
import com.nadosunbae_android.repository.review.ReviewRepository

class GetReviewDetailDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(postId: Int): ReviewDetailData {
        return repository.getReviewDetail(postId)
    }

}