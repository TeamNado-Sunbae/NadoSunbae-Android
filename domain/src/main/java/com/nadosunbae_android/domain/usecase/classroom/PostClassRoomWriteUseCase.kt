package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.ClassRoomPostWriteData
import com.nadosunbae_android.domain.model.classroom.ClassRoomPostWriteItem
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import javax.inject.Inject

class PostClassRoomWriteUseCase @Inject constructor(val repository : ClassRoomRepository) {

    suspend operator fun invoke(classRoomPostWriteItem: ClassRoomPostWriteItem) : ClassRoomPostWriteData{
        return repository.postClassRoomWrite(classRoomPostWriteItem)
    }
}