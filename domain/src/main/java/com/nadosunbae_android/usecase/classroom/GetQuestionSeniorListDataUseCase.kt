package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class GetQuestionSeniorListDataUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(userId : Int, sort : String): List<ClassRoomData>{
        return repository.getSeniorQuestionList(userId, sort)
    }
}