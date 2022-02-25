package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.WriteUpdateData
import com.nadosunbae_android.domain.model.classroom.WriteUpdateItem
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class PutWriteUpdateUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(postId : Int, writeUpdateItem : WriteUpdateItem) : WriteUpdateData{
        return repository.putWriteUpdate(postId, writeUpdateItem)
    }
}