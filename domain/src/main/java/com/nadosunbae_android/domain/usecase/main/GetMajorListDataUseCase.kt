package com.nadosunbae_android.domain.usecase.main

import com.nadosunbae_android.domain.model.main.MajorData
import com.nadosunbae_android.domain.repository.main.MainRepository

class GetMajorListDataUseCase(private val repository: MainRepository) {

    suspend operator fun invoke(universityId: Int, filter: String): List<MajorData> {
        return repository.getMajorList(universityId, filter)
    }

}