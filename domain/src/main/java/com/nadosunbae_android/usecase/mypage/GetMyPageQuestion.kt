package com.nadosunbae_android.usecase.mypage

import com.nadosunbae_android.model.mypage.MyPageQuestionData
import com.nadosunbae_android.repository.mypage.MyPageRepository

class GetMyPageQuestion(private val repository: MyPageRepository) {
    suspend fun invoke(userId: Int, sort: String) : MyPageQuestionData{
        return repository.getMyPageQuestion(userId, sort)
    }
}