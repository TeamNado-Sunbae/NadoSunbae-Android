package com.nadosunbae_android.usecase.classroom

import com.nadosunbae_android.model.classroom.QuestionDetailData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository

class GetQuestionDetailDataUseCase(val repository : ClassRoomRepository) {

    suspend operator fun invoke(postId : Int) : QuestionDetailData{
        return repository.getClassRoomQuestionDetail(postId)
    }
}