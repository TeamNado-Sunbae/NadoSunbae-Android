package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetMyPageQuestionUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userId: Int, sort: String) : MyPageQuestionData {
        return repository.getMyPageQuestion(userId, sort)
    }
}