package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.QuestionCommentWriteData
import com.nadosunbae_android.model.classroom.SeniorPersonalData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class GetSeniorPersonalDataUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(userId : Int) : SeniorPersonalData{
        return repository.getSeniorPersonal(userId)
    }
}