package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.domain.model.review.ReviewFilterItem
import com.nadosunbae_android.domain.model.review.ReviewPreviewData
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import javax.inject.Inject

class GetReviewListDataUseCase @Inject constructor(private val repository: ReviewRepository) {

    suspend operator fun invoke(reviewFilterItem: ReviewFilterItem, sort: String): List<ReviewPreviewData> {
        return repository.getReviewList(reviewFilterItem, sort)
    }

}