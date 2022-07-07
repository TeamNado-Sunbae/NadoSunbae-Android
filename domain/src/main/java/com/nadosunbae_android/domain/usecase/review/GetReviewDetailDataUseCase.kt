package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.domain.model.review.ReviewDetailData
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import javax.inject.Inject

class GetReviewDetailDataUseCase @Inject constructor(private val repository: ReviewRepository) {

    suspend operator fun invoke(postId: Int): ReviewDetailData {
        return repository.getReviewDetail(postId)
    }

}