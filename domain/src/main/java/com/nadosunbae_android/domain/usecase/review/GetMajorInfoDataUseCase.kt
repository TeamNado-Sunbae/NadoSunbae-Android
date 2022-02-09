package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.data.model.review.MajorInfoData
import com.nadosunbae_android.domain.repository.review.ReviewRepository

class GetMajorInfoDataUseCase(private val repository: ReviewRepository) {

    suspend operator fun invoke(majorId: Int): MajorInfoData {
        return repository.getMajorInfo(majorId)
    }

}