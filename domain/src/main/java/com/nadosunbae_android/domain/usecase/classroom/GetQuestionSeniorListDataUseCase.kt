package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestionSeniorListDataUseCase @Inject constructor(private val repository : ClassRoomRepository) {

    operator fun invoke(userId : Int, sort : String): Flow<List<ClassRoomData>> {
        return repository.getSeniorQuestionList(userId, sort)
    }
}