package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.domain.model.review.ReviewDeleteData
import com.nadosunbae_android.domain.repository.review.ReviewRepository

class DeleteReviewDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(postId: Int): ReviewDeleteData {
        return repository.deleteReview(postId)
    }

}