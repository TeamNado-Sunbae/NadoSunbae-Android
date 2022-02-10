package com.nadosunbae_android.usecase.review

import com.nadosunbae_android.model.review.ReviewEditItem
import com.nadosunbae_android.model.review.ReviewWriteData
import com.nadosunbae_android.repository.review.ReviewRepository

class PutReviewDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(postId: Int, reviewEditItem: ReviewEditItem): ReviewWriteData {
        return repository.putReview(postId, reviewEditItem)
    }

}