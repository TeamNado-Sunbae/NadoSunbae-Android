package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.CommentUpdateData
import com.nadosunbae_android.domain.model.classroom.CommentUpdateItem
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import javax.inject.Inject

class PutCommentUpdateUseCase @Inject constructor(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(commentId : Int, commentUpdateItem: CommentUpdateItem) : CommentUpdateData{
        return repository.putCommentUpdate(commentId, commentUpdateItem)
    }
}