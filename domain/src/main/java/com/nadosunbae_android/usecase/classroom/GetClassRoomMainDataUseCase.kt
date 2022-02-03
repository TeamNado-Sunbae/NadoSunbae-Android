package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.ClassRoomData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class GetClassRoomMainDataUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(postTypeId: Int, majorId: Int, sort: String ) : List<ClassRoomData>{
        return repository.getClassRoomMain(postTypeId, majorId, sort)
    }

}