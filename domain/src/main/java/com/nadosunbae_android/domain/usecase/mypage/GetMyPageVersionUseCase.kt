package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageReplyData
import com.nadosunbae_android.domain.model.mypage.MyPageVersionData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetMyPageVersionUseCase (private val repository: MyPageRepository) {
    suspend operator fun invoke() : MyPageVersionData {
        return repository.getMyPageVersion()
    }
}