package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageBlockData
import com.nadosunbae_android.domain.model.mypage.MyPageLikeQuestionData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetMyPageBlockUseCase (private val repository: MyPageRepository) {
    suspend operator fun invoke() : MyPageBlockData {
        return repository.getMyPageBlock()
    }
}