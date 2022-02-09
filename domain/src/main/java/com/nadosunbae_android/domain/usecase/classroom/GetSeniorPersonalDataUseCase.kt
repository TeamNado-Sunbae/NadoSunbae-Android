package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.SeniorPersonalData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class GetSeniorPersonalDataUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(userId : Int) : SeniorPersonalData{
        return repository.getSeniorPersonal(userId)
    }
}