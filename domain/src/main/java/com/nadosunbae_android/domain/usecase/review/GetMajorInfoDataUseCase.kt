package com.nadosunbae_android.domain.usecase.review

import com.nadosunbae_android.domain.model.review.MajorInfoData
import com.nadosunbae_android.domain.repository.review.ReviewRepository
import javax.inject.Inject

class GetMajorInfoDataUseCase @Inject constructor(private val repository: ReviewRepository) {

    suspend operator fun invoke(majorId: Int): MajorInfoData {
        return repository.getMajorInfo(majorId)
    }

}