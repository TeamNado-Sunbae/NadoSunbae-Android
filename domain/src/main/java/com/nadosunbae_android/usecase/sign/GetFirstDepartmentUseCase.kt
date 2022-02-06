package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.model.sign.SignMajorBottomSheet
import com.nadosunbae_android.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.repository.sign.SignRepository

class GetFirstDepartmentUseCase(private val repository : SignRepository) {
    suspend operator fun invoke(universityId : Int, filter : String) : SignMajorBottomSheet {
        return repository.getFirstDepartment(postTypeId, majorId, sort)
    }
}