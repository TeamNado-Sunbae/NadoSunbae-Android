package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.domain.model.review.BackgroundImageData
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import javax.inject.Inject

class GetBackgroundImageListDataUseCase @Inject constructor(private val repository: ReviewRepository) {

    suspend operator fun invoke(): List<BackgroundImageData> {
        return repository.getBackgroundImageList()
    }

}