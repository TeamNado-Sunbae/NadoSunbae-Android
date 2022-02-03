package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class GetSeniorDataUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(majorId: Int) : ClassRoomSeniorData{
        return repository.getClassRoomSenior(majorId)
    }
}