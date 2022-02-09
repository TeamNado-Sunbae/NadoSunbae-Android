package com.nadosunbae_android.usecase.mypage

import com.nadosunbae_android.model.mypage.MyPageQuestionData
import com.nadosunbae_android.repository.mypage.MyPageRepository

class GetMyPageQuestionUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userId: Int, sort: String) : MyPageQuestionData{
        return repository.getMyPageQuestion(userId, sort)
    }
}