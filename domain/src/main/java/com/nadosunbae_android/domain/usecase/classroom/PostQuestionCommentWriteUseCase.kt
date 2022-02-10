package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteData
import com.nadosunbae_android.domain.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class PostQuestionCommentWriteUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(questionCommentWriteItem: QuestionCommentWriteItem) : QuestionCommentWriteData{
        return repository.postQuestionCommentWrite(questionCommentWriteItem)
    }
}