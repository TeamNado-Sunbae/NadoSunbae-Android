package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageLikeQuestionData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class GetMyPageLikeQuestionUseCase @Inject constructor(private val repository: MyPageRepository) {
    suspend operator fun invoke(type: String) : MyPageLikeQuestionData {
        return repository.getMyPageLikeQuestion(type)
    }
}