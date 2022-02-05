package com.nadosunbae_android.usecase.review

import com.nadosunbae_android.model.review.ReviewFilterItem
import com.nadosunbae_android.model.review.ReviewPreviewData
import com.nadosunbae_android.repository.review.ReviewRepository

class GetReviewListDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(reviewFilterItem: ReviewFilterItem, sort: String): List<ReviewPreviewData> {
        return repository.getReviewList(reviewFilterItem, sort)
    }

}