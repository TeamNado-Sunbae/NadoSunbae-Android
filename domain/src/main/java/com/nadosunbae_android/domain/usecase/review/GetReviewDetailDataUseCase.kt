package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.domain.model.review.ReviewDetailData
import com.nadosunbae_android.domain.repository.review.ReviewRepository

class GetReviewDetailDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(postId: Int): ReviewDetailData {
        return repository.getReviewDetail(postId)
    }

}