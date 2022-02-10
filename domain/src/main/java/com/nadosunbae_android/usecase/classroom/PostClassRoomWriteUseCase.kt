package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomPostWriteData
import com.nadosunbae_android.model.classroom.ClassRoomPostWriteItem
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class PostClassRoomWriteUseCase(val repository : ClassRoomRepository) {

    suspend operator fun invoke(classRoomPostWriteItem: ClassRoomPostWriteItem) : ClassRoomPostWriteData{
        return repository.postClassRoomWrite(classRoomPostWriteItem)
    }
}