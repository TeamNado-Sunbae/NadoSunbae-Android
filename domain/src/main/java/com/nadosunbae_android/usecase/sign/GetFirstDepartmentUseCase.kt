package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.sign.SignBottomSheetItem
import com.nadosunbae_android.repository.sign.SignRepository

class GetFirstDepartmentUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(universityId : Int, filter : String) : SignBottomSheetItem {
        return repository.getFirstDepartment(universityId, filter)
    }
}