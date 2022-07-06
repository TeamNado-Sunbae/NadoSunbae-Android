package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.DeleteCommentData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import javax.inject.Inject

class DeletePostDataUseCase @Inject constructor(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(postId : Int) : DeleteCommentData{
        return repository.deletePost(postId)
    }
}