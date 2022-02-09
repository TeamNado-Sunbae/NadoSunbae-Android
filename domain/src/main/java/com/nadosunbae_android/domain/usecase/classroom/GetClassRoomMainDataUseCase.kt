package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.data.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class GetClassRoomMainDataUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(postTypeId: Int, majorId: Int, sort: String ) : List<ClassRoomData>{
        return repository.getClassRoomMain(postTypeId, majorId, sort)
    }

}