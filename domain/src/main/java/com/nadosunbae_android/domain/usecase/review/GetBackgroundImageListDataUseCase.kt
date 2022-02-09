package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.data.model.review.BackgroundImageData
import com.nadosunbae_android.domain.repository.review.ReviewRepository

class GetBackgroundImageListDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(): List<BackgroundImageData> {
        return repository.getBackgroundImageList()
    }

}