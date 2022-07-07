package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import javax.inject.Inject

class GetSeniorDataUseCase @Inject constructor(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(majorId: Int) : ClassRoomSeniorData{
        return repository.getClassRoomSenior(majorId)
    }
}