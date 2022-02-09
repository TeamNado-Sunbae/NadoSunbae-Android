package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.data.model.classroom.ClassRoomPostWriteData
import com.nadosunbae_android.data.model.classroom.ClassRoomPostWriteItem
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class PostClassRoomWriteUseCase(val repository : ClassRoomRepository) {

    suspend operator fun invoke(classRoomPostWriteItem: ClassRoomPostWriteItem) : ClassRoomPostWriteData{
        return repository.postClassRoomWrite(classRoomPostWriteItem)
    }
}