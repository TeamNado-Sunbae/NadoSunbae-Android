package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.data.model.review.ReviewFilterItem
import com.nadosunbae_android.data.model.review.ReviewPreviewData
import com.nadosunbae_android.domain.repository.review.ReviewRepository

class GetReviewListDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(reviewFilterItem: ReviewFilterItem, sort: String): List<ReviewPreviewData> {
        return repository.getReviewList(reviewFilterItem, sort)
    }

}