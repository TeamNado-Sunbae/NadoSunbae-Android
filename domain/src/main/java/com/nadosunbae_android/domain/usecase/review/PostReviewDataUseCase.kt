package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.domain.model.review.ReviewWriteData
import com.nadosunbae_android.domain.model.review.ReviewWriteItem
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import javax.inject.Inject

class PostReviewDataUseCase @Inject constructor(private val repository: ReviewRepository) {

    suspend operator fun invoke(reviewWriteItem: ReviewWriteItem): ReviewWriteData {
        return repository.postReview(reviewWriteItem)
    }

}