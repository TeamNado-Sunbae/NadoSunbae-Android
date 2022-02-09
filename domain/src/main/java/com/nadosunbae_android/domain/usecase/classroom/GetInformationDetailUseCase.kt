package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.InfoDetailData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class GetInformationDetailUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(postId: Int) : InfoDetailData{
        return repository.getInformationDetail(postId)
    }
}