package com.nadosunbae_android.usecase.review

import com.nadosunbae_android.model.review.ReviewDeleteData
import com.nadosunbae_android.repository.review.ReviewRepository

class DeleteReviewDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(postId: Int): ReviewDeleteData {
        return repository.deleteReview(postId)
    }

}