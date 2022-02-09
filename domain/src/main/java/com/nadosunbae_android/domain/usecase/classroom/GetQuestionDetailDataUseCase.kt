package com.nadosunbae_android.domain.usecase.classroom

import com.nadosunbae_android.data.model.classroom.QuestionDetailData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

class GetQuestionDetailDataUseCase(val repository : ClassRoomRepository) {

    suspend operator fun invoke(postId : Int) : QuestionDetailData{
        return repository.getClassRoomQuestionDetail(postId)
    }
}