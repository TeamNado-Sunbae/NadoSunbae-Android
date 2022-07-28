package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPagePostData
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class GetMyPagePostUseCase @Inject constructor(private val repository: MyPageRepository) {
    suspend operator fun invoke(type: String) : MyPagePostData {
        return repository.getMyPagePost(type)
    }
}