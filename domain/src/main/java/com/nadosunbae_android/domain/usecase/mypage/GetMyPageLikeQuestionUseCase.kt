package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageLikeQuestionData
import com.nadosunbae_android.domain.model.mypage.MyPageMyInfo
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetMyPageLikeQuestionUseCase (private val repository: MyPageRepository) {
    suspend operator fun invoke(type: String) : MyPageLikeQuestionData {
        return repository.getMyPageLikeQuestion(type)
    }
}