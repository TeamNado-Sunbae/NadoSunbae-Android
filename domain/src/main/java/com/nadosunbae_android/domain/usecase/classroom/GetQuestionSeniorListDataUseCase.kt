package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.data.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class GetQuestionSeniorListDataUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(userId : Int, sort : String): List<ClassRoomData>{
        return repository.getSeniorQuestionList(userId, sort)
    }
}