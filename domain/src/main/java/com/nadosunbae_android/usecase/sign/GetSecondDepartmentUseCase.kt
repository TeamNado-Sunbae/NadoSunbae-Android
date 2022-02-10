package com.nadosunbae_android.usecase.sign

import com.nadosunbae_android.model.sign.SignBottomSheetItem
import com.nadosunbae_android.repository.sign.SignRepository

class GetSecondDepartmentUseCase (private val repository : SignRepository) {
    suspend operator fun invoke(universityId : Int, filter : String) : SignBottomSheetItem {
        return repository.getFirstDepartment(universityId, filter)
    }
}