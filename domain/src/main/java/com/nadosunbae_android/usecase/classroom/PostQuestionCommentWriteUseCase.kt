package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.QuestionCommentWriteData
import com.nadosunbae_android.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class PostQuestionCommentWriteUseCase(private val repository : ClassRoomRepository) {

    suspend operator fun invoke(questionCommentWriteItem: QuestionCommentWriteItem) : QuestionCommentWriteData{
        return repository.postQuestionCommentWrite(questionCommentWriteItem)
    }
}