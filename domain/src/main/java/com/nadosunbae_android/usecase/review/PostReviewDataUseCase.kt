package com.nadosunbae_android.usecase.review

import com.nadosunbae_android.model.review.ReviewWriteData
import com.nadosunbae_android.model.review.ReviewWriteItem
import com.nadosunbae_android.repository.review.ReviewRepository

class PostReviewDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(reviewWriteItem: ReviewWriteItem): ReviewWriteData {
        return repository.postReview(reviewWriteItem)
    }

}