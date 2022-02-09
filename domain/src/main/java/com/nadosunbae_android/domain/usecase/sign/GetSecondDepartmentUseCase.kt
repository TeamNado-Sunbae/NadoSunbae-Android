package com.nadosunbae_android.domain.usecase.sign

import com.nadosunbae_android.data.model.sign.SignBottomSheetItem
import com.nadosunbae_android.domain.repository.sign.SignRepository

class GetSecondDepartmentUseCase (private val repository : SignRepository) {
    suspend operator fun invoke(universityId : Int, filter : String) : SignBottomSheetItem {
        return repository.getFirstDepartment(universityId, filter)
    }
}