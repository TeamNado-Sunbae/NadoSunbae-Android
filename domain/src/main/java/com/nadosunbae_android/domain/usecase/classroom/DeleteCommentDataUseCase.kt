package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.DeleteCommentData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class DeleteCommentDataUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(commentId : Int) : DeleteCommentData{
        return repository.deleteComment(commentId)

    }
}