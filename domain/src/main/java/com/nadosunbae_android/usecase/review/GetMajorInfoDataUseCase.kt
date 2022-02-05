package com.nadosunbae_android.usecase.review

import com.nadosunbae_android.model.review.MajorInfoData
import com.nadosunbae_android.repository.review.ReviewRepository

class GetMajorInfoDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(majorId: Int): MajorInfoData {
        return repository.getMajorInfo(majorId)
    }

}