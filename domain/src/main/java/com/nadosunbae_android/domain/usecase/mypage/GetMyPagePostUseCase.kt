package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPagePostData
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetMyPagePostUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(type: String) : MyPagePostData {
        return repository.getMyPagePost(type)
    }
}