package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.InfoDetailData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class GetInformationDetailUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(postId: Int) : InfoDetailData{
        return repository.getInformationDetail(postId)
    }
}