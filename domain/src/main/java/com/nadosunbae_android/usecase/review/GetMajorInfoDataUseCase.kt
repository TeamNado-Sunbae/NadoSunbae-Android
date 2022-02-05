package com.nadosunbae_android.usecase.review

import com.nadosunbae_android.model.review.MajorData
import com.nadosunbae_android.repository.review.ReviewRepository

class GetMajorInfoDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(majorId: Int): MajorData {
        return repository.getMajorInfo(majorId)
    }

}