package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.domain.model.review.ReviewEditItem
import com.nadosunbae_android.domain.model.review.ReviewWriteData
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import javax.inject.Inject

class PutReviewDataUseCase @Inject constructor(private val repository: ReviewRepository) {

    suspend operator fun invoke(postId: Int, reviewEditItem: ReviewEditItem): ReviewWriteData {
        return repository.putReview(postId, reviewEditItem)
    }

}