package com.nadosunbae_android.usecase.main

import com.nadosunbae_android.model.main.MajorData
import com.nadosunbae_android.repository.main.MainRepository

class GetMajorListDataUseCase(private val repository: MainRepository) {

    suspend operator fun invoke(universityId: Int, filter: String): List<MajorData> {
        return repository.getMajorList(universityId, filter)
    }

}