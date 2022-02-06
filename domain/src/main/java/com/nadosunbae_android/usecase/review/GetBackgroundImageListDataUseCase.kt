package com.nadosunbae_android.usecase.review

import com.nadosunbae_android.model.review.BackgroundImageData
import com.nadosunbae_android.repository.review.ReviewRepository

class GetBackgroundImageListDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(): List<BackgroundImageData> {
        return repository.getBackgroundImageList()
    }

}