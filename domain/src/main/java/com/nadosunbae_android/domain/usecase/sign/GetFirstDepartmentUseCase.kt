package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.sign.SignBottomSheetItem
import com.nadosunbae_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class GetFirstDepartmentUseCase @Inject constructor(private val repository : SignRepository) {
    suspend operator fun invoke(universityId : Int, filter : String) : SignBottomSheetItem {
        return repository.getFirstDepartment(universityId, filter)
    }
}