package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.SeniorPersonalData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetSeniorPersonalDataUseCase @Inject constructor(private val repository : ClassRoomRepository) {

    operator fun invoke(userId : Int) : Flow<SeniorPersonalData> {
        return repository.getSeniorPersonal(userId)
    }
}